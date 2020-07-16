package bohdan.varchenko.data.local.dao

import androidx.room.*
import bohdan.varchenko.data.local.AppDatabase
import bohdan.varchenko.data.local.entities.RepositoryEntity
import bohdan.varchenko.data.local.entities.SearchQueryRepositoryEntity
import bohdan.varchenko.data.mappers.toRepositoryEntity
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery

@Dao
internal abstract class RepositoryDao(private val database: AppDatabase) {

    @Query(
        """
            SELECT * FROM ${AppDatabase.TABLE_REPOSITORIES}
            WHERE ${RepositoryEntity.NAME} LIKE '%' || :name || '%' COLLATE NOCASE
            ORDER BY ${RepositoryEntity.STARS} DESC
            LIMIT :count
            OFFSET :offset
        """
    )
    abstract fun localSearch(name: String, offset: Int, count: Int): List<RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(repositories: List<RepositoryEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entity: SearchQueryRepositoryEntity)

    @Transaction
    open fun insertNewSearchResult(
        searchQuery: SearchQuery,
        repositories: List<Repository>
    ) {
        insert(repositories.map { it.toRepositoryEntity() })
        repositories.forEach {
            insert(
                SearchQueryRepositoryEntity(
                    searchQueryId = searchQuery.id,
                    repositoryId = it.id
                )
            )
        }
    }

    @Query(
        """
        SELECT * FROM ${AppDatabase.TABLE_REPOSITORIES} 
        WHERE ${RepositoryEntity.ID} in (
            SELECT ${SearchQueryRepositoryEntity.REPOSITORY_ID} FROM ${AppDatabase.TABLE_SEARCH_QUERY_REPOSITORY}
            WHERE ${SearchQueryRepositoryEntity.SEARCH_QUERY_ID} = :searchId
        ) 
            ORDER BY ${RepositoryEntity.STARS} DESC
            LIMIT :count
            OFFSET :offset
    """
    )
    abstract fun recoverRecentSearch(
        searchId: Long,
        offset: Int,
        count: Int
    ): List<RepositoryEntity>

    @Query(
        """
            UPDATE ${AppDatabase.TABLE_REPOSITORIES} SET ${RepositoryEntity.IS_VIEWED} = :isViewed
            WHERE ${RepositoryEntity.ID} = :repositoryId
        """
    )
    abstract fun markRepositoryAsViewed(repositoryId: Long, isViewed: Boolean)

    @Query("SELECT * FROM ${AppDatabase.TABLE_REPOSITORIES}")
    abstract fun getAll(): List<RepositoryEntity>
}