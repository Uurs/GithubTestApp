package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class RepositoryRemoveRecentSearchUseCase
@Inject constructor(
    private val dataSource: RepositoryDataSource
) : RepositoryUseCase.RemoveRecentSearch {

    override fun execute(query: SearchQuery): Completable {
        return Completable.fromAction {
            dataSource.deleteRecentSearch(query)
        }
    }
}