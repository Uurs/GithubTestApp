package bohdan.varchenko.gittestproject.screens.searchrepository

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseActivity
import bohdan.varchenko.gittestproject.screens.Launcher
import bohdan.varchenko.gittestproject.utils.hideKeyboard
import bohdan.varchenko.gittestproject.utils.views.addOnScrollListener
import bohdan.varchenko.gittestproject.utils.views.gone
import bohdan.varchenko.gittestproject.utils.views.visible
import kotlinx.android.synthetic.main.activity_repository_list.*

internal class SearchRepositoryActivity : BaseActivity<SearchRepositoryViewModel>() {
    override val layoutResource: Int
        get() = R.layout.activity_repository_list
    override val viewModelClass: Class<SearchRepositoryViewModel>
        get() = SearchRepositoryViewModel::class.java

    private lateinit var adapter: RepositoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeForState(this) { renderState(it) }
        viewModel.subscribeForEvents(this) { onEvent(it) }
        adapter = RepositoryListAdapter(imageLoader) { viewModel.previewRepository(it) }
        rvRepositoryList.adapter = adapter
        rvRepositoryList.addOnScrollListener { viewModel.loadMore(it) }
        bSearch.setOnClickListener {
            viewModel.newSearch(etSearch.text.toString())
            hideKeyboard(etSearch)
        }
        recentSearch?.run {
            viewModel.newSearch(this)
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
                    this,
                    event.repository.htmlUrl
                )
            SearchRepositoryViewModel.Event.FailedToLoadRepositories ->
                Toast.makeText(
                    this,
                    R.string.common_message_something_went_wrong,
                    Toast.LENGTH_SHORT
                )
                    .show()
        }
    }

    companion object {
        private const val KEY_RECENT_SEARCH = "recent_search"

        private val SearchRepositoryActivity.recentSearch: String?
            get() = intent.getStringExtra(KEY_RECENT_SEARCH)

        fun getIntent(context: Context, recentSearch: SearchQuery?): Intent =
            Intent(context, SearchRepositoryActivity::class.java)
                .apply {
                    if (recentSearch != null) {
                        putExtra(KEY_RECENT_SEARCH, recentSearch.text)
                    }
                }
    }
}