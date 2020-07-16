package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.Repository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import org.junit.Assert.*
import org.junit.Test

internal class RepositoryMarkAsViewedUseCaseTest: BaseUseCaseTest() {

    @Test
    fun `test positive flow`() = block<Dto> {
        whenever(dataSource.markAsRead(any())) doReturn Completable.complete()
        useCase.execute(Repository(0, "", "", "", 0, "", "", false, 100))
            .test()
            .assertNoErrors()
        verify(dataSource, times(1)).markAsRead(0)
    }

    @Test
    fun `test negative flow`() = block<Dto> {
        whenever(dataSource.markAsRead(any())) doReturn Completable.error(Exception())
        useCase.execute(Repository(0, "", "", "", 0, "", "", false, 100))
            .test()
            .assertError(Exception::class.java)
        verify(dataSource, times(1)).markAsRead(0)
    }

    data class Dto(
       val useCase: RepositoryMarkAsViewedUseCase,
       val dataSource: RepositoryDataSource
    )
}