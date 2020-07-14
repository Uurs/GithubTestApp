package bohdan.varchenko.domain.usecases

import bohdan.varchenko.domain.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserUseCase {

    interface Login {
        fun execute(
            login: String,
            password: String
        ): Single<User>
    }

    interface Logout {
        fun execute(): Completable
    }

    interface GetCurrentUser {
        fun execute(): Single<User>
    }
}