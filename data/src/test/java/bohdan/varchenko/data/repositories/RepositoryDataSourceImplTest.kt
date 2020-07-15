package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.dto.SearchResponseDto
import bohdan.varchenko.domain.models.SearchQuery
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Single
import org.junit.Test

internal class RepositoryDataSourceImplTest {
    private val api: RepositoryApi = mock()
    private val searchQueryDao: SearchQueryDao = mock()
    private val repositoryDao: RepositoryDao = mock()

    @Test
    fun `check getRecentSearch`() {
        whenever(searchQueryDao.getAll()) doReturn emptyList()
        val repo = getRepositoryDataSource()
        repo.getRecentSearch()
            .test()
            .assertValue(emptyList<SearchQuery>())
            .assertNoErrors()

        verify(searchQueryDao, times(1)).getAll()
        verifyZeroInteractions(repositoryDao, api)
    }

    @Test
    fun `check insertNewRecentSearch`() {
        whenever(searchQueryDao.insert(SearchQueryEntity(0, "new", 0))) doReturn 0
        whenever(searchQueryDao.getById(0)) doReturn SearchQueryEntity(0, "new", 0)
        val repo = getRepositoryDataSource()
        repo.insertNewRecentSearch("new")
        verify(searchQueryDao, times(1)).insert(any())
        verify(searchQueryDao, times(1)).getById(any())
        verifyZeroInteractions(repositoryDao, api)
    }

    @Test
    fun `check updateRecentSearch`() {
        val repo = getRepositoryDataSource()
        repo.updateRecentSearch(SearchQuery(100, "new", 0))
        verify(searchQueryDao, times(1)).update(SearchQueryEntity(100, "new", 0))
        verifyZeroInteractions(repositoryDao, api)
    }

    @Test
    fun `check deleteRecentSearch`() {
        val repo = getRepositoryDataSource()
        repo.deleteRecentSearch(SearchQuery(100, "new", 0))
        verify(searchQueryDao, times(1)).delete(100)
        verifyZeroInteractions(repositoryDao, api)
    }

    @Test
    fun `check search positive flow`() {
        whenever(api.search(any(), any(), any(), any(), any())) doReturn Single.just(
            SearchResponseDto(true, emptyList(), 0)
        )
        val repo = getRepositoryDataSource()
        repo.search(SearchQuery(0, "123", 0), 0, 30, true)
            .test()
            .assertNoErrors()
        verify(api, times(1)).search(eq("123"), eq(0), eq(15), any(), any())
        verify(api, times(1)).search(eq("123"), eq(1), eq(15), any(), any())
        verifyZeroInteractions(repositoryDao, searchQueryDao)
    }

    private fun getRepositoryDataSource(): RepositoryDataSourceImpl =
        RepositoryDataSourceImpl(
            api = api,
            searchQueryDao = searchQueryDao,
            repositoryDao = repositoryDao
        )
}