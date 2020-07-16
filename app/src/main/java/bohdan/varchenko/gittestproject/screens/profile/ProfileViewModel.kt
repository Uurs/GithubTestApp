package bohdan.varchenko.gittestproject.screens.profile

import bohdan.varchenko.domain.di.UserUseCaseModule_ProvidesGetCurrentUserFactory
import bohdan.varchenko.domain.models.User
import bohdan.varchenko.domain.usecases.UserUseCase
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

internal class ProfileViewModel
@Inject constructor(
    private val getCurrentUserFactory: UserUseCase.GetCurrentUser
) : StatefulViewModel<ProfileViewModel.State, ProfileViewModel.Event>() {

    init {
        updateCurrentUser()
    }

    fun updateCurrentUser() {
        getCurrentUserFactory.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { putState(State(user = it)) },
                { postEvent(Event.FailedToLoadUser) }
            )
    }

    data class State(
        val user: User?
    )

    sealed class Event {
        object FailedToLoadUser: Event()
    }
}