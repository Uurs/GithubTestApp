package bohdan.varchenko.gittestproject.screens.recentsearchlist

import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecentSearchViewModel
@Inject constructor(
    private val getRecentSearch: RepositoryUseCase.GetRecentSearch
): StatefulViewModel<RecentSearchViewModel.State, RecentSearchViewModel.Event>() {

    init {
        putState(State(emptyList()))
    }

    fun refresh() {
        getRecentSearch.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateState { copy(list = it) } },
                { postEvent(Event.FailedToLoadRecentSearch) }
            )
            .cache()
    }

    fun openRecentSearch(searchQuery: SearchQuery) {
        postEvent(Event.OpenRecentSearch(searchQuery))
    }

    fun initNewSearch() {
        postEvent(Event.StartNewSearch)
    }

    data class State(
        val list: List<SearchQuery>
    )

    sealed class Event {
        data class OpenRecentSearch(val query: SearchQuery): Event()
        object StartNewSearch: Event()
        object FailedToLoadRecentSearch: Event()
    }
}