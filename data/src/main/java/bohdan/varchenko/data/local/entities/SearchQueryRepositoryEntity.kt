package bohdan.varchenko.data.local.entities

import androidx.room.*
import bohdan.varchenko.data.local.AppDatabase.Companion.TABLE_SEARCH_QUERY_REPOSITORY
import bohdan.varchenko.data.local.entities.SearchQueryRepositoryEntity.Companion.INDEX
import bohdan.varchenko.data.local.entities.SearchQueryRepositoryEntity.Companion.REPOSITORY_ID
import bohdan.varchenko.data.local.entities.SearchQueryRepositoryEntity.Companion.SEARCH_QUERY_ID

@Entity(
    tableName = TABLE_SEARCH_QUERY_REPOSITORY,
    indices = [
        Index(
            SEARCH_QUERY_ID, REPOSITORY_ID,
            name = INDEX,
            unique = true
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = SearchQueryEntity::class,
            parentColumns = [SearchQueryEntity.ID],
            childColumns = [SEARCH_QUERY_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RepositoryEntity::class,
            parentColumns = [RepositoryEntity.ID],
            childColumns = [REPOSITORY_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class SearchQueryRepositoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = SEARCH_QUERY_ID) val searchQueryId: Long,
    @ColumnInfo(name = REPOSITORY_ID) val repositoryId: Long
) {
    companion object {
        internal const val INDEX = "${TABLE_SEARCH_QUERY_REPOSITORY}_unique"
        internal const val SEARCH_QUERY_ID = "${TABLE_SEARCH_QUERY_REPOSITORY}_search_query_id"
        internal const val REPOSITORY_ID = "${TABLE_SEARCH_QUERY_REPOSITORY}_repository_id"
    }
}