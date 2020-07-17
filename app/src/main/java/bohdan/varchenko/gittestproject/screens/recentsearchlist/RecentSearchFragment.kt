package bohdan.varchenko.gittestproject.screens.recentsearchlist

import android.os.Bundle
import android.view.View
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseFragment
import bohdan.varchenko.gittestproject.screens.Launcher
import kotlinx.android.synthetic.main.fragment_recent_search.*

class RecentSearchFragment : BaseFragment<RecentSearchViewModel>() {

    override val layoutResource: Int
        get() = R.layout.fragment_recent_search

    override val viewModelClass: Class<RecentSearchViewModel>
        get() = RecentSearchViewModel::class.java

    private lateinit var adapter: RecentSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribeForState(this) { renderState(it) }
        viewModel.subscribeForEvents(this) { onEvent(it) }
        btnInitNewSearch.setOnClickListener { viewModel.initNewSearch() }
        adapter = RecentSearchAdapter {
                viewModel.openRecentSearch(it)
            }
        rvRecentSearch.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    private fun renderState(state: RecentSearchViewModel.State) {
        adapter.recentSearchList = state.list
    }

    private fun onEvent(event: RecentSearchViewModel.Event) {
        when (event) {
            is RecentSearchViewModel.Event.OpenRecentSearch ->
                Launcher.launchSearchRepositoryActivity(requireContext(), event.query)
            RecentSearchViewModel.Event.StartNewSearch ->
                Launcher.launchSearchRepositoryActivity(requireContext(), null)
            RecentSearchViewModel.Event.FailedToLoadRecentSearch -> TODO()
        }
    }
}