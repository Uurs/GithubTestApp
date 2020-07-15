package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class RepositoryDataSourceImpl
@Inject constructor(
    private val api: RepositoryApi
) : RepositoryDataSource {

    override fun search(
        name: String,
        page: Int,
        count: Int,
        orderDescending: Boolean
    ): Single<List<Repository>> {
        TODO("Not yet implemented")
    }

    override fun getRecentSearch(): Observable<List<SearchQuery>> {
        TODO("Not yet implemented")
    }

    override fun insertNewRecentSearch(name: String) {
        TODO("Not yet implemented")
    }

    override fun updateRecentSearch(updatedSearch: SearchQuery) {
        TODO("Not yet implemented")
    }

    override fun deleteRecentSearch(query: SearchQuery) {
        TODO("Not yet implemented")
    }
}