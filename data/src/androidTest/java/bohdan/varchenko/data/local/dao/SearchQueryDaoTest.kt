package bohdan.varchenko.data.local.dao

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import bohdan.varchenko.data.local.createDatabase
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchQueryDaoTest {
    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testInsertAllAndGetAll() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        repeat(100) {
            dao.insert(SearchQueryEntity(text = "text_$it", orderPosition = it))
        }
        assertEquals(100, dao.getAll().size)
    }

    @Test
    fun testUpdateSearchQuery() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        var query = SearchQueryEntity(text = "test string", orderPosition = 0)
        val id = dao.insert(query)
        query = query.copy(id = id)
        assertEquals(query, dao.getAll().first())
        dao.update(query.copy(orderPosition = 100))
        assertEquals(query.copy(orderPosition = 100), dao.getAll().first())
    }

    @Test
    fun testDeleteSearchQuery() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        val query = SearchQueryEntity(text = "test string", orderPosition = 0)
        val id = dao.insert(query)
        assertEquals(1, dao.getAll().size)

        dao.delete(id)
        assertEquals(0, dao.getAll().size)
    }

    @Test
    fun testGetById() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        val query = SearchQueryEntity(text = "test string", orderPosition = 0)
        val id = dao.insert(query)
        assertEquals(query.copy(id = id), dao.getById(id))
    }

    @Test
    fun testGetByIdNullReturned() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        assertNull(dao.getById(0))
    }

    @Test
    fun testDeleteNotExistingValue() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        dao.delete(100)
    }

    @Test
    fun testSeveralAddSameName() {
        val database = createDatabase(context)
        val dao = database.getSearchQueryDao()
        val searchQuery = SearchQueryEntity(text = "123", orderPosition = 0)
        dao.insert(searchQuery)
        dao.insert(searchQuery)
        assertEquals(1, dao.getAll().size)
    }
}