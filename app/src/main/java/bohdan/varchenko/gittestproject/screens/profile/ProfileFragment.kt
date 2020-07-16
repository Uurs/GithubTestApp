package bohdan.varchenko.gittestproject.screens.profile

import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseFragment

internal class ProfileFragment: BaseFragment<ProfileViewModel>() {

    override val layoutResource: Int
        get() = R.layout.fragment_profile
    override val viewModelClass: Class<ProfileViewModel>
        get() = ProfileViewModel::class.java
}