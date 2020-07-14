package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class RepositoryUpdateRecentSearchPositionUseCase
@Inject constructor(
    private val dataSource: RepositoryDataSource
) : RepositoryUseCase.UpdateRecentSearchPosition {

    override fun execute(query: SearchQuery, newPosition: Int): Completable {
        return Completable.fromAction {
            dataSource.updateRecentSearch(query.copy(orderPosition = newPosition))
        }
    }
}