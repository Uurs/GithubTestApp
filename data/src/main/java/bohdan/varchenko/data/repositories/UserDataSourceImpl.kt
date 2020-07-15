package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.local.UserStorage
import bohdan.varchenko.data.remote.UserApi
import bohdan.varchenko.domain.datasource.UserDataSource
import bohdan.varchenko.domain.exceptions.NotAuthorizedException
import bohdan.varchenko.domain.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class UserDataSourceImpl
@Inject constructor(
    private val userApi: UserApi,
    private val userStorage: UserStorage
) : UserDataSource {

    override fun login(name: String, password: String): Single<User> {
        return userApi.login(name, password)
            .doOnSuccess { userStorage.storeUser(it) }
    }

    override fun logout(): Completable {
        return Completable.fromAction {
            userStorage.clearUserData()
        }
    }

    override fun getCurrentUser(): Single<User> {
        return Single.fromCallable {
            userStorage.getUser() ?: throw NotAuthorizedException()
        }
    }
}