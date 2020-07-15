package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.models.SearchQuery
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Test

internal class RepositorySearchUseCaseTest : BaseUseCaseTest() {

    @Test
    fun `positive flow`() = block<Dto> {
        whenever(dataSource.insertNewRecentSearch("")) doReturn SearchQuery(0, "", 0)
        whenever(dataSource.search(any(), any(), any(), any())) doReturn
                Single.just(emptyList())

        useCase.execute("", 0, true)
            .test()
            .assertNoErrors()

        verify(dataSource, times(1)).search(any(), any(), any(), any())
    }

    @Test
    fun `negative flow no internet connection`() = block<Dto>() {
        whenever(dataSource.insertNewRecentSearch("")) doReturn SearchQuery(0, "", 0)
        var i = 0
        whenever(dataSource.search(any(), any(), any(), any())) doReturn
                Single.fromCallable {
                    return@fromCallable if (i++ == 0) throw NoInternetConnection()
                    else emptyList<Repository>()
                }
        whenever(internetObserver.observeNetworkState()) doReturn
                Observable.just(false, true)

        useCase.execute("", 0, true)
            .test()
            .assertValueAt(0) { it.isNotEmpty() && it.data!!.isEmpty() }
            .assertNoErrors()

        verify(dataSource, times(2)).search(any(), any(), any(), any())
    }

    @Test
    fun `negative flow some exception`() = block<Dto> {
        whenever(dataSource.insertNewRecentSearch("")) doReturn SearchQuery(0, "", 0)
        whenever(dataSource.search(any(), any(), any(), any())) doReturn
                Single.fromCallable<List<Repository>> { throw Exception() }
        whenever(internetObserver.observeNetworkState()) doReturn
                Observable.just(false, true)

        useCase.execute("", 0, true)
            .test()
            .assertError(Exception::class.java)

        verify(dataSource, times(1)).search(any(), any(), any(), any())
    }

    private data class Dto(
        val useCase: RepositorySearchUseCase,
        val dataSource: RepositoryDataSource,
        val internetObserver: InternetObserver
    )
}