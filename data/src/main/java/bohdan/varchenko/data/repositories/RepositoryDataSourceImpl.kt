package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.local.dao.RepositoryDao
import bohdan.varchenko.data.local.dao.SearchQueryDao
import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.data.mappers.toRepository
import bohdan.varchenko.data.mappers.toRepositoryEntity
import bohdan.varchenko.data.mappers.toSearchQuery
import bohdan.varchenko.data.mappers.toSearchQueryEntity
import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.dto.SearchResponseDto
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
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
        return Single.zip(
            api.search(
                q = queryRequest,
                page = page * 2,
                count = count / 2,
                sort = "star",
                orderBy = if (orderDescending) "desc" else "asc"
            )
                .subscribeOn(Schedulers.io()),
            api.search(
                q = queryRequest,
                page = page * 2 + 1,
                count = count / 2,
                sort = "star",
                orderBy = if (orderDescending) "desc" else "asc"
            )
                .subscribeOn(Schedulers.io()),
            BiFunction { t1: SearchResponseDto, t2: SearchResponseDto ->
                t1.searchResponseItemDtos + t2.searchResponseItemDtos
            }
        )
            .map { response -> response.map { it.toRepository() } }
            .doOnSuccess { list ->
                if (list.isNotEmpty())
                    repositoryDao.insert(list.map { it.toRepositoryEntity() })
            }
            .onErrorReturn {
                repositoryDao.search(query.text, page * count, count)
                    .map { it.toRepository() }
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