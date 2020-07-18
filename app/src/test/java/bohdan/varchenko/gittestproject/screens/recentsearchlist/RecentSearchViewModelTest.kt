package bohdan.varchenko.gittestproject.screens.recentsearchlist

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.domain.models.User
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.domain.usecases.UserUseCase
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
    private val getCurrentUser: UserUseCase.GetCurrentUser = mock()
    private val queryList = (0..3).map { SearchQuery(it.toLong(), "text $it", 0) }
    private val user = User(100, "asdasd", "nasdasd", "asdfsadf")

    @Test
    fun `test refresh positive flow`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        whenever(getCurrentUser.execute()) doReturn
                Single.just(DataWrapper.from(user))

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()

        viewModel.refresh()

        testStateObserver.assertValueCount(2)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))
            .assertValueAt(1, RecentSearchViewModel.State(queryList, user))
    }

    @Test
    fun `test refresh negative flow -- error during get recent search`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.error(Exception())

        whenever(getCurrentUser.execute()) doReturn
                Single.just(DataWrapper.from(user))

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()

        testStateObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))
        testEventsObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.Event.FailedToLoadInitialData)
    }

    @Test
    fun `test refresh negative flow -- user unauthorized`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        whenever(getCurrentUser.execute()) doReturn
                Single.just(DataWrapper.error(Exception()))

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()

        testStateObserver.assertValueCount(2)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))
            .assertValueAt(1, RecentSearchViewModel.State(queryList, null))
        testEventsObserver.assertNoValues()
    }

    @Test
    fun `test refresh negative flow -- error get current user`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        whenever(getCurrentUser.execute()) doReturn
                Single.error(Exception())

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()

        testStateObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))
        testEventsObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.Event.FailedToLoadInitialData)
    }

    @Test
    fun `test new search positive flow`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        whenever(getCurrentUser.execute()) doReturn
                Single.just(DataWrapper.from(user))

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()
        viewModel.initNewSearch()

        testStateObserver.assertValueCount(2)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))
            .assertValueAt(1, RecentSearchViewModel.State(queryList, user))

        testEventsObserver.assertValueAt(0, RecentSearchViewModel.Event.StartNewSearch)
    }

    @Test
    fun `test new search negative flow -- user is not authorized`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        whenever(getCurrentUser.execute()) doReturn
                Single.just(DataWrapper.error(Exception()))

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()
        viewModel.initNewSearch()

        testStateObserver.assertValueCount(2)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))
            .assertValueAt(1, RecentSearchViewModel.State(queryList, null))

        testEventsObserver.assertValueAt(0, RecentSearchViewModel.Event.CantInitNewSearch)
    }

    @Test
    fun `test new search negative flow -- error during get current user`() {
        whenever(getRecentSearch.execute()) doReturn
                Single.just(queryList)

        whenever(getCurrentUser.execute()) doReturn
                Single.error(Exception())

        val viewModel = getViewModel()
        val testStateObserver = viewModel.toObservableState().test()
        val testEventsObserver = viewModel.toObservableEvents().test()

        viewModel.refresh()
        viewModel.initNewSearch()

        testStateObserver.assertValueCount(1)
            .assertValueAt(0, RecentSearchViewModel.State(emptyList(), null))

        testEventsObserver.assertValueAt(0, RecentSearchViewModel.Event.FailedToLoadInitialData)
            .assertValueAt(1, RecentSearchViewModel.Event.CantInitNewSearch)
    }

    private fun getViewModel(): RecentSearchViewModel {
        return RecentSearchViewModel(
            getRecentSearch = getRecentSearch,
            getCurrentUser = getCurrentUser
        )
    }
}