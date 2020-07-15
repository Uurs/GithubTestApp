package bohdan.varchenko.data.local.dao

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import bohdan.varchenko.data.local.createDatabase
import bohdan.varchenko.data.local.entities.RepositoryEntity
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
        val repositories = (0..100).map {
            RepositoryEntity(
                it,
                "name_$it",
                "description_$it",
                "html_$it",
                it,
                "owner_$it",
                "avatar_$it",
                false
            )
        }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(repositories, repo.getAll())
    }

    @Test
    fun testSearchInLocal() {
        val database = createDatabase(context)
        val repositories = (0..100).map {
            RepositoryEntity(
                it,
                "name_$it",
                "description_$it",
                "html_$it",
                it,
                "owner_$it",
                "avatar_$it",
                false
            )
        }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(30, repo.search("name", 0, 30).size)
    }

    @Test
    fun testSearchInLocalOnlyOne() {
        val database = createDatabase(context)
        val repositories = (0..100).map {
            RepositoryEntity(
                it,
                "name_$it",
                "description_$it",
                "html_$it",
                it,
                "owner_$it",
                "avatar_$it",
                false
            )
        }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(1, repo.search("31", 0, 30).size)
    }

    @Test
    fun testSearchInLocalNothingFound() {
        val database = createDatabase(context)
        val repositories = (0..100).map {
            RepositoryEntity(
                it,
                "name_$it",
                "description_$it",
                "html_$it",
                it,
                "owner_$it",
                "avatar_$it",
                false
            )
        }
        val repo = database.getRepositoryDao()
        repo.insert(repositories)
        Assert.assertEquals(0, repo.search("310", 0, 30).size)
    }

    @Test
    fun testSearchInEmptyLocal() {
        val database = createDatabase(context)
        val repo = database.getRepositoryDao()
        Assert.assertEquals(0, repo.search("name", 0, 30).size)
    }
}