package bohdan.varchenko.domain.datasource

import bohdan.varchenko.domain.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserDataSource {
    fun login(token: String): Single<User>

    fun logout(): Completable

    fun getCurrentUser(): Single<User>
}