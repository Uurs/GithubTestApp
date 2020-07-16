package bohdan.varchenko.domain.datasource

import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RepositoryDataSource {

    fun search(
        query: SearchQuery,
        page: Int,
        count: Int,
        orderDescending: Boolean
    ): Single<List<Repository>>

    fun markAsRead(repositoryId: Long): Completable

    fun getRecentSearch(): Single<List<SearchQuery>>

    fun insertNewRecentSearch(name: String): SearchQuery

    fun updateRecentSearch(updatedSearch: SearchQuery)

    fun deleteRecentSearch(query: SearchQuery)
}