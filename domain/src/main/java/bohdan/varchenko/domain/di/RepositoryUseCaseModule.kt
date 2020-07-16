package bohdan.varchenko.domain.di

import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.domain.usecases.impl.repository.*
import bohdan.varchenko.domain.usecases.impl.repository.RepositoryGetRecentSearchUseCase
import bohdan.varchenko.domain.usecases.impl.repository.RepositoryMarkAsViewedUseCase
import bohdan.varchenko.domain.usecases.impl.repository.RepositoryRemoveRecentSearchUseCase
import bohdan.varchenko.domain.usecases.impl.repository.RepositorySearchUseCase
import bohdan.varchenko.domain.usecases.impl.repository.RepositoryUpdateRecentSearchPositionUseCase
import dagger.Module
import dagger.Provides

@Module
open class RepositoryUseCaseModule {

    @Provides
    fun provideUpdateRecentSearchPosition(
        dataSource: RepositoryDataSource
    ): RepositoryUseCase.UpdateRecentSearchPosition =
        RepositoryUpdateRecentSearchPositionUseCase(dataSource)

    @Provides
    fun provideRemoveRecentSearch(
        dataSource: RepositoryDataSource
    ): RepositoryUseCase.RemoveRecentSearch =
        RepositoryRemoveRecentSearchUseCase(dataSource)

    @Provides
    fun provideGetRecentSearch(
        dataSource: RepositoryDataSource
    ): RepositoryUseCase.GetRecentSearch =
        RepositoryGetRecentSearchUseCase(dataSource)

    @Provides
    fun provideSearch(
        dataSource: RepositoryDataSource,
        internetObserver: InternetObserver
    ): RepositoryUseCase.Search =
        RepositorySearchUseCase(dataSource, internetObserver)

    @Provides
    fun provideMarkAsRead(
        dataSource: RepositoryDataSource
    ): RepositoryUseCase.MarkAsViewed =
        RepositoryMarkAsViewedUseCase(dataSource)
}