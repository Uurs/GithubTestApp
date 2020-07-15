package bohdan.varchenko.data.di

import bohdan.varchenko.data.di.local.RepositorySubComponent
import bohdan.varchenko.data.repositories.RepositoryDataSourceImpl
import bohdan.varchenko.data.repositories.UserDataSourceImpl
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.datasource.UserDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    subcomponents = [
        RepositorySubComponent::class
    ]
)
open class DataSourceModule {

    @Singleton
    @Provides
    fun providesRepositorySubComponent(
        builder: RepositorySubComponent.Builder
    ): RepositorySubComponent {
        return builder.build()
    }

    @Provides
    fun provideRepositoryDataSource(component: RepositorySubComponent): RepositoryDataSource {
        return RepositoryDataSourceImpl(
            api = component.repositoryApi(),
            searchQueryDao = component.searchQueryDao(),
            repositoryDao = component.repositoryDao()
        )
    }

    @Provides
    fun provideUserDataSource(component: RepositorySubComponent): UserDataSource {
        return UserDataSourceImpl(
            userApi = component.userApi(),
            userStorage = component.userStorage()
        )
    }
}