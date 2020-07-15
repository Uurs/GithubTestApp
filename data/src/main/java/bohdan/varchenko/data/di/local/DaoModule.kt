package bohdan.varchenko.data.di.local

import android.content.Context
import bohdan.varchenko.data.local.AppDatabase
import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import dagger.Module
import dagger.Provides

@Module
internal open class DaoModule {

    @Application
    @Provides
    fun providesDatabase(context: Context): AppDatabase {
        return AppDatabase.createDatabase(context)
    }

    @Provides
    fun providesRepositoryDao(database: AppDatabase): RepositoryDao {
        return database.getRepositoryDao()
    }

    @Provides
    fun providesSearchQueryDao(database: AppDatabase): SearchQueryDao {
        return database.getSearchQueryDao()
    }
}