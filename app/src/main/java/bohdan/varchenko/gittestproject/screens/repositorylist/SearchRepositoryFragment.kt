package bohdan.varchenko.gittestproject.screens.repositorylist

import android.os.Bundle
import android.view.View
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseFragment
import bohdan.varchenko.gittestproject.utils.hideKeyboard
import bohdan.varchenko.gittestproject.utils.views.addOnScrollListener
import kotlinx.android.synthetic.main.fragment_repository_list.*

class SearchRepositoryFragment : BaseFragment<SearchRepositoryViewModel>() {
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
    }

    private fun onEvent(event: SearchRepositoryViewModel.Event) {

    }
}