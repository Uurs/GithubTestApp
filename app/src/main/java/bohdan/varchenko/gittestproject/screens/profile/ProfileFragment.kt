package bohdan.varchenko.gittestproject.screens.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.base.BaseFragment
import bohdan.varchenko.gittestproject.utils.views.gone
import bohdan.varchenko.gittestproject.utils.views.visible
import kotlinx.android.synthetic.main.fragment_profile.*

internal class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val layoutResource: Int
        get() = R.layout.fragment_profile
    override val viewModelClass: Class<ProfileViewModel>
        get() = ProfileViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscribeForEvents(this) { onEvent(it) }
        viewModel.subscribeForState(this) { renderState(it) }
        bLogin.setOnClickListener { showLoginDialog() }
        bLogout.setOnClickListener { showLogoutDialog() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateCurrentUser()
    }

    private fun renderState(state: ProfileViewModel.State) {
        if (state.user != null) {
            llLoginHint.gone()
            llProfile.visible()
            imageLoader.loadImage(ivProfileAvatar, state.user.avatarUrl, R.drawable.ic_person)
            tvProfileName.text = state.user.name
        } else {
            llLoginHint.visible()
            llProfile.gone()
            tvProfileName.text = ""
            ivProfileAvatar.setImageDrawable(null)
        }
    }

    private fun onEvent(event: ProfileViewModel.Event) {
        when (event) {
            ProfileViewModel.Event.FailedToLogin ->
                showMessage(R.string.common_error_failed_to_make_request)
            ProfileViewModel.Event.FailedToLogout ->
                showMessage(R.string.common_error_failed_to_make_request)
        }
    }

    private fun showLoginDialog() {
        createLoginDialog(requireContext()) { viewModel.login(it) }
            .show()
    }

    private fun showLogoutDialog() {
        createLogoutDialog(requireContext()) { viewModel.logout() }
            .show()
    }

    private fun showMessage(messageStringId: Int) {
        Toast.makeText(requireContext(), messageStringId, Toast.LENGTH_SHORT).show()
    }
}