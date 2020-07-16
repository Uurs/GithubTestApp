package bohdan.varchenko.gittestproject.screens.searchrepository

import android.os.Bundle
import android.view.View
import android.widget.Toast
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseFragment
import bohdan.varchenko.gittestproject.screens.Launcher
import bohdan.varchenko.gittestproject.utils.hideKeyboard
import bohdan.varchenko.gittestproject.utils.views.addOnScrollListener
import bohdan.varchenko.gittestproject.utils.views.gone
import bohdan.varchenko.gittestproject.utils.views.visible
import kotlinx.android.synthetic.main.fragment_repository_list.*

internal class SearchRepositoryFragment : BaseFragment<SearchRepositoryViewModel>() {
    override val layoutResource: Int
        get() = R.layout.fragment_repository_list
    override val viewModelClass: Class<SearchRepositoryViewModel>
        get() = SearchRepositoryViewModel::class.java

    private lateinit var adapter: RepositoryListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribeForState(this) { renderState(it) }
        viewModel.subscribeForEvents(this) { onEvent(it) }
        adapter = RepositoryListAdapter(imageLoader) { viewModel.previewRepository(it) }
        rvRepositoryList.adapter = adapter
        rvRepositoryList.addOnScrollListener { viewModel.loadMore(it) }
        bSearch.setOnClickListener {
            viewModel.newSearch(etSearch.text.toString())
            requireContext().hideKeyboard(etSearch)
        }
    }

    private fun renderState(state: SearchRepositoryViewModel.State) {
        if (etSearch.text?.isBlank() == true && state.lastSearch.isNotBlank()) {
            etSearch.setText(state.lastSearch)
        }
        adapter.items = state.list
        if (state.loading) {
            loading.visible()
        } else {
            loading.gone()
        }
    }

    private fun onEvent(event: SearchRepositoryViewModel.Event) {
        when (event) {
            is SearchRepositoryViewModel.Event.OpenRepositoryDetails ->
                Launcher.launchRepositoryDetailsActivity(
                    requireContext(),
                    event.repository.htmlUrl
                )
            SearchRepositoryViewModel.Event.FailedToLoadRepositories ->
                Toast.makeText(
                    requireContext(),
                    R.string.common_message_something_went_wrong,
                    Toast.LENGTH_SHORT
                )
                    .show()
        }
    }
}