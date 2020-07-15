package bohdan.varchenko.data.di

import bohdan.varchenko.data.di.local.RepositorySubComponent
import bohdan.varchenko.data.repositories.RepositoryDataSourceImpl
import bohdan.varchenko.data.repositories.UserDataSourceImpl
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.datasource.UserDataSource
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [
        RepositorySubComponent::class
    ]
)
open class DataSourceModule {

    @Provides
    fun provideRepositoryDataSource(component: RepositorySubComponent): RepositoryDataSource {
        val repositoryApi = component.repositoryApi()
        return RepositoryDataSourceImpl(repositoryApi)
    }

    @Provides
    fun provideUserDataSource(component: RepositorySubComponent): UserDataSource {
        val userApi = component.userApi()
        return UserDataSourceImpl(userApi)
    }
}