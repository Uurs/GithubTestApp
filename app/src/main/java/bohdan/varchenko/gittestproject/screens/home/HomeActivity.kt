package bohdan.varchenko.gittestproject.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseActivity
import bohdan.varchenko.gittestproject.screens.profile.ProfileFragment
import bohdan.varchenko.gittestproject.screens.searchrepository.SearchRepositoryFragment
import kotlinx.android.synthetic.main.activity_home.*

internal class HomeActivity : BaseActivity<HomeViewModel>() {

    override val layoutResource: Int
        get() = R.layout.activity_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeForEvents(this) { onEvent(it) }
        viewModel.subscribeForState(this) { renderState(it) }

        bnvHome.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_search -> showRecentSearchScreen()
                R.id.menu_profile -> showProfileScreen()
            }
            true
        }
        bnvHome.selectedItemId = R.id.menu_profile
    }

    private fun renderState(state: HomeViewModel.State) {

    }

    private fun onEvent(event: HomeViewModel.Event) {

    }

    fun showProfileScreen() {
        showScreen(ProfileFragment())
    }

    fun showRecentSearchScreen() {
        showScreen(SearchRepositoryFragment())
    }

    private fun showScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment)
            .commit()
    }
}