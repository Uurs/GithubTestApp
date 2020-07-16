package bohdan.varchenko.gittestproject.screens.home

import android.os.Bundle
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseActivity
import bohdan.varchenko.gittestproject.screens.repositorylist.RepositoryListFragment

class HomeActivity : BaseActivity<HomeViewModel>() {

    override val layoutResource: Int
        get() = R.layout.activity_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeForEvents(this) { onEvent(it) }
        viewModel.subscribeForState(this) { renderState(it) }

        supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, RepositoryListFragment())
            .commit()
    }

    private fun renderState(state: HomeViewModel.State) {

    }

    private fun onEvent(event: HomeViewModel.Event) {

    }
}