package bohdan.varchenko.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import bohdan.varchenko.data.local.entities.RepositoryEntity
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.data.local.entities.SearchQueryRepositoryEntity

@Database(
    entities = [
        RepositoryEntity::class,
        SearchQueryEntity::class,
        SearchQueryRepositoryEntity::class
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepositoryDao(): RepositoryDao

    abstract fun getSearchQueryDao(): SearchQueryDao

    companion object {
        internal const val DATABASE_VERSION = 1
        internal const val DATABASE_NAME = "main_database"
        internal const val TABLE_REPOSITORIES = "repositories"
        internal const val TABLE_SEARCH_QUERY = "search_query"
        internal const val TABLE_SEARCH_QUERY_REPOSITORY = "search_query_repositories"

        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}