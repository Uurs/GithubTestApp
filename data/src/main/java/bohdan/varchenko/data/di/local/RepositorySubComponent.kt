package bohdan.varchenko.data.di.local

import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.UserApi
import dagger.Subcomponent
import javax.inject.Scope

@Application
@Subcomponent(
    modules = [
        ApiModule::class,
        DaoModule::class
    ]
)
abstract class RepositorySubComponent {

    internal abstract fun userApi(): UserApi

    internal abstract fun repositoryApi(): RepositoryApi

    internal abstract fun repositoryDao(): RepositoryDao

    internal abstract fun searchQueryDao(): SearchQueryDao

    @Subcomponent.Builder
    interface Builder{
        fun build(): RepositorySubComponent
    }
}

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Application