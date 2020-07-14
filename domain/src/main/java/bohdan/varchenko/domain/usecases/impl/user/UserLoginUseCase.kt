package bohdan.varchenko.domain.usecases.impl.user

import bohdan.varchenko.domain.datasource.UserDataSource
import bohdan.varchenko.domain.models.User
import bohdan.varchenko.domain.usecases.UserUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class UserLoginUseCase
@Inject constructor(
    private val dataSource: UserDataSource
) : UserUseCase.Login {

    override fun execute(login: String, password: String): Single<User> {
        return dataSource.login(login, password)
    }
}