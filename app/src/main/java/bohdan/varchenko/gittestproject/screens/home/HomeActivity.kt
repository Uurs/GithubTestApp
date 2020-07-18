package bohdan.varchenko.gittestproject.screens.home

import android.os.Bundle
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.AbsActivity
import bohdan.varchenko.gittestproject.base.BaseFragment
import bohdan.varchenko.gittestproject.screens.profile.ProfileFragment
import bohdan.varchenko.gittestproject.screens.recentsearchlist.RecentSearchFragment
import kotlinx.android.synthetic.main.activity_home.*

internal class HomeActivity : AbsActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnvHome.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_search -> showRecentSearchScreen()
                R.id.menu_profile -> showProfileScreen()
            }
            true
        }
        bnvHome.selectedItemId = R.id.menu_profile
    }

    private fun showRecentSearchScreen() {
        showScreen(RecentSearchFragment::class.java)
    }

    private fun showProfileScreen() {
        showScreen(ProfileFragment::class.java)
    }

    private fun showScreen(fragment: Class<out BaseFragment<*>>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment.newInstance())
            .commit()

    }
}