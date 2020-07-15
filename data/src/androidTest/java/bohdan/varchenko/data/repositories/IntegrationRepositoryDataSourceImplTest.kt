package bohdan.varchenko.data.repositories

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import bohdan.varchenko.data.local.AppDatabase
import bohdan.varchenko.data.local.createDatabase
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.data.mappers.toRepository
import bohdan.varchenko.data.mappers.toSearchQuery
import bohdan.varchenko.data.readStringFromAssets
import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.dto.SearchResponseDto
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.SearchQuery
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

internal class IntegrationRepositoryDataSourceImplTest {
    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var repository: RepositoryDataSource
    private lateinit var mockWebServer: MockWebServer
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val testRetrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        val api = testRetrofit.create(RepositoryApi::class.java)

        database = createDatabase(context)

        repository = RepositoryDataSourceImpl(
            api = api,
            searchQueryDao = database.getSearchQueryDao(),
            repositoryDao = database.getRepositoryDao()
        )
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSearchPositiveFlow() {
        val responseStringPage0 = readStringFromAssets(context, "search_tetris_200_page_0.json")
        val responseStringPage1 = readStringFromAssets(context, "search_tetris_200_page_1.json")
        val expectedResult = with(ObjectMapper()) {
            val page0 = readValue(responseStringPage0, SearchResponseDto::class.java)
            val page1 = readValue(responseStringPage1, SearchResponseDto::class.java)
            page0.searchResponseItemDtos + page1.searchResponseItemDtos
        }
            .map { it.toRepository() }
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseStringPage0)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseStringPage1)
        )

        val searchQuery = with(database.getSearchQueryDao()) {
            val id = insert(SearchQueryEntity(text = "tetris", orderPosition = 0))
            getById(id)
        }!!.toSearchQuery()

        repository.search(searchQuery, 0, 30, true)
            .test()
            .assertNoErrors()
            .awaitCount(1)
            .assertValue { it == expectedResult }
    }

    @Test
    fun testSearchNegativeFlowNoInternetConnection() {
        repository.search(SearchQuery(0, "tetris", 0), 0, 30, true)
            .test()
            .await()
            .assertValue { it.isEmpty() }
            .assertNoErrors()
    }

    @Test
    fun testSearchSecondRequestFailed() {
        val responseStringPage0 = readStringFromAssets(context, "search_tetris_200_page_0.json")
        val responseStringPage1 = readStringFromAssets(context, "search_tetris_200_page_1.json")
        val expectedResult = with(ObjectMapper()) {
            val page0 = readValue(responseStringPage0, SearchResponseDto::class.java)
            val page1 = readValue(responseStringPage1, SearchResponseDto::class.java)
            page0.searchResponseItemDtos + page1.searchResponseItemDtos
        }
            .map { it.toRepository() }
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseStringPage0)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseStringPage1)
        )

        val searchQuery = with(database.getSearchQueryDao()) {
            val id = insert(SearchQueryEntity(text = "tetris", orderPosition = 0))
            getById(id)
        }!!.toSearchQuery()

        repository.search(searchQuery, 0, 30, true)
            .test()
            .assertNoErrors()
            .awaitCount(1)
            .assertValue { it == expectedResult }

        repository.search(searchQuery, 0, 30, true)
            .test()
            .assertNoErrors()
            .await()
            .assertValue { it == expectedResult }
    }
}