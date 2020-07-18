package bohdan.varchenko.gittestproject.screens.recentsearchlist

import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.gittestproject.BaseViewModelTest
import bohdan.varchenko.gittestproject.toObservableEvents
import bohdan.varchenko.gittestproject.toObservableState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class RecentSearchViewModelTest : BaseViewModelTest() {
    private val getRecentSearch: RepositoryUseCase.GetRecentSearch = mock()
    private val queryList = (0..3).map { SearchQuery(it.toLong(), "text $it", 0) }

    @Test
    fun `test refresh positive flow`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()

        viewModel.refresh()

        testStateObserver.assertValueCount(2)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList()))
            .assertValueAt(1, RecentSearchViewModel.State(queryList))
    }

    @Test
    fun `test refresh negative flow -- error during get recent search`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.error(Exception())

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()

        testStateObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList()))
        testEventsObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.Event.FailedToLoadRecentSearch)
    }

    private fun getViewModel(): RecentSearchViewModel {
        return RecentSearchViewModel(
            getRecentSearch = getRecentSearch
        )
    }
}