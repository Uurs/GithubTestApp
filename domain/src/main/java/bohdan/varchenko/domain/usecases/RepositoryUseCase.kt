package bohdan.varchenko.domain.usecases

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RepositoryUseCase {

    interface GetRecentSearch {
        fun execute(): Single<List<SearchQuery>>
    }

    interface RemoveRecentSearch {
        fun execute(query: SearchQuery): Completable
    }

    interface UpdateRecentSearchPosition {
        fun execute(query: SearchQuery, newPosition: Int): Completable
    }

    interface Search {
        fun execute(
            name: String,
            page: Int,
            orderDescending: Boolean
        ): Single<DataWrapper<List<Repository>>>
    }
}