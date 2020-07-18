package bohdan.varchenko.gittestproject.screens.recentsearchlist

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.models.User
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.domain.usecases.UserUseCase
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecentSearchViewModel
@Inject constructor(
    private val getRecentSearch: RepositoryUseCase.GetRecentSearch,
    private val getCurrentUser: UserUseCase.GetCurrentUser
) : StatefulViewModel<RecentSearchViewModel.State, RecentSearchViewModel.Event>() {

    init {
        putState(State(emptyList(), null))
    }

    fun refresh() {
        Single.zip(
            getCurrentUser.execute(),
            getRecentSearch.execute(),
            BiFunction { user: DataWrapper<User>, list: List<SearchQuery> -> user to list }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val (user, list) = it
                    updateState { copy(list = list, user = user.data) }
                },
                { postEvent(Event.FailedToLoadRecentSearch) }
            )
            .cache()
    }

    fun openRecentSearch(searchQuery: SearchQuery) {
        postEvent(Event.OpenRecentSearch(searchQuery))
    }

    fun initNewSearch() {
        if (state.user == null) {
            postEvent(Event.CantInitNewSearch)
            return
        }
        postEvent(Event.StartNewSearch)
    }

    data class State(
        val list: List<SearchQuery>,
        val user: User?
    )

    sealed class Event {
        data class OpenRecentSearch(val query: SearchQuery) : Event()
        object CantInitNewSearch: Event()
        object StartNewSearch : Event()
        object FailedToLoadRecentSearch : Event()
    }
}