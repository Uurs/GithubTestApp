package bohdan.varchenko.data.local.dao

import androidx.room.*
import bohdan.varchenko.data.local.AppDatabase
import bohdan.varchenko.data.local.entities.SearchQueryEntity

@Dao
internal interface SearchQueryDao {

    @Query("SELECT * FROM ${AppDatabase.TABLE_SEARCH_QUERY}")
    fun getAll(): List<SearchQueryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(query: SearchQueryEntity): Long

    @Update
    fun update(query: SearchQueryEntity)

    @Query("DELETE FROM ${AppDatabase.TABLE_SEARCH_QUERY} WHERE ${SearchQueryEntity.ID} = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM ${AppDatabase.TABLE_SEARCH_QUERY} WHERE ${SearchQueryEntity.ID} = :id")
    fun getById(id: Long) : SearchQueryEntity?
}