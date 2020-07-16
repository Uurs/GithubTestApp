package bohdan.varchenko.gittestproject.screens.repositorylist

import android.os.Bundle
import android.view.View
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_repository_list.*

class RepositoryListFragment : BaseFragment<RepositoryListViewModel>() {
    override val layoutResource: Int
        get() = R.layout.fragment_repository_list
    override val viewModelClass: Class<RepositoryListViewModel>
        get() = RepositoryListViewModel::class.java

    private val adapter = RepositoryListAdapter { viewModel.previewRepository(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribeForState(this) { renderState(it) }
        viewModel.subscribeForEvents(this) { onEvent(it) }
        rvRepositoryList.adapter = adapter
        bSearch.setOnClickListener { viewModel.newSearch(etSearch.text.toString()) }
    }

    private fun renderState(state: RepositoryListViewModel.State) {
        adapter.items = state.list
    }

    private fun onEvent(event: RepositoryListViewModel.Event) {

    }
}