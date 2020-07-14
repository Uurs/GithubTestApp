package bohdan.varchenko.gittestproject.screens.home

import android.os.Bundle
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseActivity

class HomeActivity : BaseActivity<HomeViewModel>() {

    override val layoutResource: Int
        get() = R.layout.activity_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeForEvents(this) { onEvent(it) }
        viewModel.subscribeForState(this) { renderState(it) }
    }

    private fun renderState(state: HomeViewModel.State) {

    }

    private fun onEvent(event: HomeViewModel.Event) {

    }
}