package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class RepositoryGetRecentSearchUseCase
@Inject constructor(
    private val dataSource: RepositoryDataSource
) : RepositoryUseCase.GetRecentSearch {

    override fun execute(): Single<List<SearchQuery>> {
        return dataSource.getRecentSearch()
    }
}