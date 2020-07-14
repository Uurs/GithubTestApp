package bohdan.varchenko.gittestproject.screens.home

import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val getRecentSearch: RepositoryUseCase.GetRecentSearch
) : StatefulViewModel<HomeViewModel.State, HomeViewModel.Event>() {

    data class State(
        val recentSearchList: List<SearchQuery>
    )

    sealed class Event
}