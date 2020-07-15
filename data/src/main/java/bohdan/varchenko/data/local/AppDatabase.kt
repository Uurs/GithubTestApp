package bohdan.varchenko.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import bohdan.varchenko.data.local.entities.RepositoryEntity
import bohdan.varchenko.data.local.entities.SearchQueryEntity

@Database(
    entities = [
        RepositoryEntity::class,
        SearchQueryEntity::class
    ],
    version = AppDatabase.DATABASE_VERSION
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepositoryDao(): RepositoryDao

    abstract fun getSearchQueryDao(): SearchQueryDao

    companion object {
        internal const val DATABASE_VERSION = 1
        internal const val DATABASE_NAME = "main_database"
        internal const val TABLE_REPOSITORIES = "repositories"
        internal const val TABLE_SEARCH_QUERY = "search_query"

        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}