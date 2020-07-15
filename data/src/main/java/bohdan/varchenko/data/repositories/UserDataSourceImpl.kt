package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.remote.UserApi
import bohdan.varchenko.domain.datasource.UserDataSource
import bohdan.varchenko.domain.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class UserDataSourceImpl
@Inject constructor(
    private val userApi: UserApi
) : UserDataSource {

    override fun login(name: String, password: String): Single<User> {
        TODO("Not yet implemented")
    }

    override fun logout(): Completable {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): Single<User> {
        TODO("Not yet implemented")
    }
}