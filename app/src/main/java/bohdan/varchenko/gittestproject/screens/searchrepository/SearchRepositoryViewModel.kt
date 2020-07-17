package bohdan.varchenko.gittestproject.screens.searchrepository

import bohdan.varchenko.domain.SearchConfig.SEARCH_RESULTS_PER_PAGE
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

internal class SearchRepositoryViewModel
@Inject constructor(
    private val searchUseCase: RepositoryUseCase.Search,
    private val markAsViewedUseCase: RepositoryUseCase.MarkAsViewed
) : StatefulViewModel<SearchRepositoryViewModel.State, SearchRepositoryViewModel.Event>() {

    init {
        putState(State(false, "", emptyList(), 0))
    }

    fun newSearch(searchText: String) {
        if (state.loading) return
        updateState {
            copy(
                lastSearch = searchText,
                loading = true,
                list = emptyList(),
                currentPage = 0
            )
        }
        searchUseCase.execute(searchText, 0, true)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ -> updateState { copy(loading = false) } }
            .subscribe(
                { updateState { copy(list = list + (it.data ?: emptyList())) } },
                { postEvent(Event.FailedToLoadRepositories) }
            )
            .cache()
    }

    fun previewRepository(repository: Repository) {
        markAsViewedUseCase.execute(repository)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                updateState {
                    copy(list = list.map {
                        if (it.id == repository.id) it.copy(
                            isViewed = true
                        ) else it
                    })
                }
            }
            .subscribe(
                { postEvent(Event.OpenRepositoryDetails(repository)) },
                { postEvent(Event.FailedToOpenRepository) }
            )
            .cache()
    }

    fun loadMore(currentPosition: Int) {
        if (currentPosition < state.currentPage * SEARCH_RESULTS_PER_PAGE
            + SEARCH_RESULTS_PER_PAGE / 2
            || state.loading
        ) return
        updateState { copy(loading = true) }
        searchUseCase.execute(state.lastSearch, state.currentPage + 1, true)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { updateState { copy(loading = false) } }
            .subscribe(
                {
                    if (it.error != null) {
                        postEvent(Event.FailedToLoadRepositories)
                    }  else {
                        updateState {
                            copy(
                                list = list + (it.data ?: emptyList()),
                                currentPage = currentPage + 1
                            )
                        }
                    }
                },
                { postEvent(Event.FailedToLoadRepositories) }
            )
            .cache()
    }

    data class State(
        val loading: Boolean,
        val lastSearch: String,
        val list: List<Repository>,
        val currentPage: Int
    )

    sealed class Event {
        data class OpenRepositoryDetails(val repository: Repository) : Event()
        object FailedToLoadRepositories : Event()
        object FailedToOpenRepository: Event()
    }
}