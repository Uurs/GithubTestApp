package bohdan.varchenko.gittestproject.screens.profile

import bohdan.varchenko.domain.models.User
import bohdan.varchenko.domain.usecases.UserUseCase
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

internal class ProfileViewModel
@Inject constructor(
    private val loginUserUseCase: UserUseCase.Login,
    private val logoutUserUseCase: UserUseCase.Logout,
    private val getCurrentUserFactory: UserUseCase.GetCurrentUser
) : StatefulViewModel<ProfileViewModel.State, ProfileViewModel.Event>() {

    init {
        putState(State(null, false))
        updateCurrentUser()
    }

    fun login(token: String) {
        updateState { copy(loading = true) }
        loginUserUseCase.execute(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { updateState { copy(loading = false) } }
            .subscribe(
                { updateState { copy(user = it) } },
                { postEvent(Event.FailedToLogin) }
            )
            .cache()
    }

    fun updateCurrentUser() {
        updateState { copy(loading = true) }
        getCurrentUserFactory.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { updateState { copy(loading = false) } }
            .subscribe(
                { updateState { copy(user = it.data) } },
                { postEvent(Event.FailedToLoadUser) }
            )
    }

    fun logout() {
        updateState { copy(loading = true) }
        logoutUserUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateState { copy(user = null) } },
                { postEvent(Event.FailedToLogout) }
            )
            .cache()
    }

    data class State(
        val user: User?,
        val loading: Boolean
    )

    sealed class Event {
        object FailedToLoadUser : Event()
        object FailedToLogin : Event()
        object FailedToLogout : Event()
    }
}