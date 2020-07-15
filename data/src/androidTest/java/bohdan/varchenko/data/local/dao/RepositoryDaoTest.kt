package bohdan.varchenko.data.local.dao

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import bohdan.varchenko.data.local.createDatabase
import bohdan.varchenko.data.local.entities.RepositoryEntity
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.data.mappers.toRepository
import bohdan.varchenko.data.mappers.toSearchQuery
import bohdan.varchenko.domain.models.Repository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class RepositoryDaoTest {
    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testInsertGetRepositoriesList() {
        val database = createDatabase(context)
        val repositories = (0..100)
            .map { it.toLong() }
            .map {
                RepositoryEntity(
                    it,
                    "name_$it",
                    "description_$it",
                    "html_$it",
                    it,
                    "owner_$it",
                    "avatar_$it",
                    false,
                    100
                )
            }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(repositories, repo.getAll())
    }

    @Test
    fun testSearchInLocal() {
        val database = createDatabase(context)
        val repositories = (0..100)
            .map { it.toLong() }
            .map {
                RepositoryEntity(
                    it,
                    "name_$it",
                    "description_$it",
                    "html_$it",
                    it,
                    "owner_$it",
                    "avatar_$it",
                    false,
                    100
                )
            }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(30, repo.localSearch("name", 0, 30).size)
    }

    @Test
    fun testSearchInLocalOnlyOne() {
        val database = createDatabase(context)
        val repositories = (0..100)
            .map { it.toLong() }
            .map {
                RepositoryEntity(
                    it,
                    "name_$it",
                    "description_$it",
                    "html_$it",
                    it,
                    "owner_$it",
                    "avatar_$it",
                    false,
                    100
                )
            }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(1, repo.localSearch("31", 0, 30).size)
    }

    @Test
    fun testSearchInLocalNothingFound() {
        val database = createDatabase(context)
        val repositories = (0..100)
            .map { it.toLong() }
            .map {
                RepositoryEntity(
                    it,
                    "name_$it",
                    "description_$it",
                    "html_$it",
                    it,
                    "owner_$it",
                    "avatar_$it",
                    false,
                    100
                )
            }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(0, repo.localSearch("310", 0, 30).size)
    }

    @Test
    fun testSearchInEmptyLocal() {
        val database = createDatabase(context)
        val repo = database.getRepositoryDao()
        Assert.assertEquals(0, repo.localSearch("name", 0, 30).size)
    }

    @Test
    fun testSearchCaseInsensitive() {
        val database = createDatabase(context)
        val repositories = (0..100)
            .map { it.toLong() }
            .map {
                RepositoryEntity(
                    it,
                    "name_$it",
                    "description_$it",
                    "html_$it",
                    it,
                    "owner_$it",
                    "avatar_$it",
                    false,
                    100
                )
            }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(
            repositories.filter { it.name.contains("E_4", true) },
            repo.localSearch("E_4", 0, 100)
        )
    }

    @Test
    fun recoverRecentSearchTest() {
        val database = createDatabase(context)
        val repositories = (0..5)
            .map { it.toLong() }
            .map {
                Repository(
                    it,
                    "name_$it",
                    "description_$it",
                    "html_$it",
                    it,
                    "owner_$it",
                    "avatar_$it",
                    false,
                    (1000 - it).toInt()
                )
            }
        val searchQuery = with(database.getSearchQueryDao()) {
            val id = insert(SearchQueryEntity(text = "name", orderPosition = 0))
            getById(id)
        }!!.toSearchQuery()

        val repo = database.getRepositoryDao()
        repo.insertNewSearchResult(searchQuery, repositories)

        Assert.assertEquals(
            repositories,
            repo.recoverRecentSearch(searchQuery.id, 0, 100).map { it.toRepository() }
        )
    }
}