package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.SearchConfig
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.domain.utils.handleNoInternetConnection
import bohdan.varchenko.domain.wrap
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class RepositorySearchUseCase
@Inject constructor(
    private val dataSource: RepositoryDataSource,
    private val internetObserver: InternetObserver
) : RepositoryUseCase.Search {

    override fun execute(
        name: String,
        page: Int,
        orderDescending: Boolean
    ): Single<DataWrapper<List<Repository>>> {
        return Single.fromCallable {
            dataSource.insertNewRecentSearch(name)
        }
            .flatMap {
                dataSource.search(
                    it,
                    page,
                    SearchConfig.SEARCH_RESULTS_PER_PAGE,
                    orderDescending
                )

            }
            .map { it.wrap() }
            .handleNoInternetConnection(internetObserver)
    }
}