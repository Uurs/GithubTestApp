package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.data.mappers.toRepository
import bohdan.varchenko.data.mappers.toSearchQuery
import bohdan.varchenko.data.mappers.toSearchQueryEntity
import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.dto.SearchResponseDto
import bohdan.varchenko.domain.SearchConfig
import bohdan.varchenko.domain.SearchConfig.DEFAULT_SORT_BY
import bohdan.varchenko.domain.SearchConfig.ORDER_ASC
import bohdan.varchenko.domain.SearchConfig.ORDER_DESC
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

internal class RepositoryDataSourceImpl
@Inject constructor(
    private val api: RepositoryApi,
    private val searchQueryDao: SearchQueryDao,
    private val repositoryDao: RepositoryDao
) : RepositoryDataSource {

    override fun search(
        query: SearchQuery,
        page: Int,
        count: Int,
        orderDescending: Boolean
    ): Single<List<Repository>> {
        val queryRequest = createQuery(query)
        val order = if (orderDescending) ORDER_DESC else ORDER_ASC
        return Single.zip(
            generateRequests(
                queryRequest,
                page,
                count,
                DEFAULT_SORT_BY,
                order
            )
        ) { response ->
            response.mapNotNull { dto -> (dto as? SearchResponseDto) }
                .let { dto ->
                    if (orderDescending)
                        dto.sortedByDescending {
                            it.searchResponseItemDtos.last().stargazersCount
                        }
                    else
                        dto.sortedBy {
                            it.searchResponseItemDtos.last().stargazersCount
                        }
                }
                .map { it.searchResponseItemDtos }
                .flatten()
                .map { dto -> dto.toRepository() }
        }
            .onErrorResumeNext {
                Single.fromCallable {
                    repositoryDao.recoverRecentSearch(query.id, page * count, count)
                        .map { it.toRepository() }
                }
            }
            .doOnSuccess { list ->
                if (list.isNotEmpty())
                    repositoryDao.insertNewSearchResult(query, list)
            }
    }

    override fun markAsRead(repositoryId: Long): Completable {
        return Completable.fromAction {
            repositoryDao.markRepositoryAsViewed(repositoryId, true)
        }
    }

    override fun getRecentSearch(): Single<List<SearchQuery>> {
        return Single.fromCallable {
            searchQueryDao.getAll().map { it.toSearchQuery() }
        }
    }

    override fun insertNewRecentSearch(name: String): SearchQuery {
        val id = searchQueryDao.insert(SearchQueryEntity(text = name, orderPosition = 0))
        return searchQueryDao.getById(id)!!.toSearchQuery()
    }

    override fun updateRecentSearch(updatedSearch: SearchQuery) {
        searchQueryDao.update(updatedSearch.toSearchQueryEntity())
    }

    override fun deleteRecentSearch(query: SearchQuery) {
        searchQueryDao.delete(query.id)
    }

    private fun generateRequests(
        query: String,
        page: Int,
        count: Int,
        sort: String,
        orderBy: String
    ): List<Single<SearchResponseDto>> {
        val initialRequestPage = page * SearchConfig.MAX_THREAD_COUNT
        val requestCount = count / SearchConfig.MAX_THREAD_COUNT
        return (0 until SearchConfig.MAX_THREAD_COUNT).map {
            val requestPage = initialRequestPage + it
            getRequest(query, requestPage, requestCount, sort, orderBy)
        }
    }

    private fun getRequest(
        query: String,
        page: Int,
        count: Int,
        sort: String,
        orderBy: String
    ): Single<SearchResponseDto> =
        api.search(query, page , count, sort, orderBy)
            .subscribeOn(Schedulers.io())

    private fun createQuery(query: SearchQuery): String {
        return query.text.split(" ").let {
            if (it.size > 1) {
                it.joinToString { "+" }
            } else {
                it.first()
            }
        }
    }
}