package bohdan.varchenko.domain.usecases.impl.user

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.UserDataSource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import org.junit.Test

internal class UserLogoutUseCaseTest : BaseUseCaseTest() {

    @Test
    fun `positive flow`() = block<Dto> {
        whenever(dataSource.logout()) doReturn Completable.complete()
        useCase.execute()
            .test()
            .assertNoErrors()
    }

    @Test
    fun `negative flow`() = block<Dto> {
        whenever(dataSource.logout()) doReturn Completable.error(Exception())
        useCase.execute()
            .test()
            .assertError(Throwable::class.java)
    }

    private data class Dto(
        val useCase: UserLogoutUseCase,
        val dataSource: UserDataSource
    )
}

