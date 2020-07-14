package bohdan.varchenko.domain.usecases.impl.user

import bohdan.varchenko.domain.datasource.UserDataSource
import bohdan.varchenko.domain.usecases.UserUseCase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class UserLogoutUseCase
@Inject constructor(
    private val dataSource: UserDataSource
) : UserUseCase.Logout {

    override fun execute(): Completable {
        return dataSource.logout()
    }
}