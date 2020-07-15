package bohdan.varchenko.domain.usecases.impl.user

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.UserDataSource
import bohdan.varchenko.domain.models.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

internal class UserLoginUseCaseTest : BaseUseCaseTest() {

    @Test
    fun `positive flow`() = block<Dto> {
        whenever(dataSource.login(any(), any())) doReturn Single.just(User("id"))
        useCase.execute("name", "password")
            .test()
            .assertNoErrors()
    }

    @Test
    fun `negative flow`() = block<Dto> {
        whenever(dataSource.login(any(), any())) doReturn Single.error(Exception())
        useCase.execute("name", "password")
            .test()
            .assertError(Throwable::class.java)
    }

    private data class Dto(
        val useCase: UserLoginUseCase,
        val dataSource: UserDataSource
    )
}