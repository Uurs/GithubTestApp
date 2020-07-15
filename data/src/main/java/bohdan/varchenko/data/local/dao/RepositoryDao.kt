package bohdan.varchenko.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bohdan.varchenko.data.local.AppDatabase
import bohdan.varchenko.data.local.entities.RepositoryEntity

@Dao
internal interface RepositoryDao {

    @Query(
        """
            SELECT * FROM ${AppDatabase.TABLE_REPOSITORIES} 
            WHERE ${RepositoryEntity.NAME} LIKE '%' || :name || '%'
            LIMIT :count
            OFFSET :offset
        """
    )
    fun search(name: String, offset: Int, count: Int): List<RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM ${AppDatabase.TABLE_REPOSITORIES}")
    fun getAll(): List<RepositoryEntity>
}