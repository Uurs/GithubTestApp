package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

internal class RepositoryGetRecentSearchUseCaseTest :
    BaseUseCaseTest() {

    @Test
    fun `positive flow`() = block<Dto> {
        whenever(dataSource.getRecentSearch()) doReturn Single.just(emptyList())

        useCase.execute()
            .test()
            .assertNoErrors()

        verify(dataSource, times(1)).getRecentSearch()
    }

    @Test
    fun `negative flow`() = block<Dto> {
        whenever(dataSource.getRecentSearch()) doReturn
                Single.error(Exception())

        useCase.execute()
            .test()
            .assertError(Exception::class.java)

        verify(dataSource, times(1)).getRecentSearch()
    }

    private data class Dto(
        val useCase: RepositoryGetRecentSearchUseCase,
        val dataSource: RepositoryDataSource
    )
}