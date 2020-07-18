package bohdan.varchenko.gittestproject.screens.searchrepository

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.SearchConfig.SEARCH_RESULTS_PER_PAGE
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.gittestproject.BaseViewModelTest
import bohdan.varchenko.gittestproject.toObservableEvents
import bohdan.varchenko.gittestproject.toObservableState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class SearchRepositoryViewModelTest : BaseViewModelTest() {
    private val searchUseCase: RepositoryUseCase.Search = mock()
    private val markAsViewedUseCase: RepositoryUseCase.MarkAsViewed = mock()

    private fun getViewModel(): SearchRepositoryViewModel = SearchRepositoryViewModel(
        searchUseCase = searchUseCase,
        markAsViewedUseCase = markAsViewedUseCase
    )

    @Test
    fun `check new search positive flow`() {
        val repositories = listOf(
            Repository(
                100,
                "123",
                null,
                "asdasd",
                1000,
                "123",
                "asdasd",
                false,
                100
            )
        )

        whenever(searchUseCase.execute(any(), any(), any())) doReturn
                Single.just(DataWrapper.from(repositories))
        val viewModel = getViewModel()

        val stateObservable = viewModel.toObservableState().test()

        viewModel.newSearch("123")

        stateObservable.assertNoErrors()
            .assertValueAt(0, SearchRepositoryViewModel.State(false, "", emptyList(), 0))
            .assertValueAt(1, SearchRepositoryViewModel.State(true, "123", emptyList(), 0))
            .assertValueAt(2, SearchRepositoryViewModel.State(false, "123", emptyList(), 0))
            .assertValueAt(3, SearchRepositoryViewModel.State(false, "123", repositories, 0))

        verify(searchUseCase, times(1)).execute("123", 0, true)
    }

    @Test
    fun `check new search negative flow`() {
        whenever(searchUseCase.execute(any(), any(), any())) doReturn
                Single.error(NoInternetConnection())
        val viewModel = getViewModel()

        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")

        stateObservable.assertNoErrors()
            .assertValueAt(0, SearchRepositoryViewModel.State(false, "", emptyList(), 0))
            .assertValueAt(1, SearchRepositoryViewModel.State(true, "123", emptyList(), 0))
            .assertValueAt(2, SearchRepositoryViewModel.State(false, "123", emptyList(), 0))

        eventsObservable.assertValueAt(0, SearchRepositoryViewModel.Event.FailedToLoadRepositories)
        verify(searchUseCase, times(1)).execute("123", 0, true)
    }

    @Test
    fun `check preview repository positive flow`() {
        val repository = Repository(
            100,
            "123",
            null,
            "asdasd",
            1000,
            "123",
            "asdasd",
            false,
            100
        )
        whenever(markAsViewedUseCase.execute(any())) doReturn Completable.complete()
        whenever(searchUseCase.execute(any(), any(), any())) doReturn
                Single.just(DataWrapper.from(listOf(repository)))

        val viewModel = getViewModel()

        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")
        viewModel.previewRepository(repository)

        stateObservable.assertValueAt(
            4,
            SearchRepositoryViewModel.State(
                false,
                "123",
                listOf(repository.copy(isViewed = true)),
                0
            )
        )

        eventsObservable.assertValueAt(
            0,
            SearchRepositoryViewModel.Event.OpenRepositoryDetails(repository)
        )
    }

    @Test
    fun `check preview repository negative flow`() {
        val repository = Repository(
            100,
            "123",
            null,
            "asdasd",
            1000,
            "123",
            "asdasd",
            false,
            100
        )
        whenever(markAsViewedUseCase.execute(any())) doReturn Completable.error(Exception())
        whenever(searchUseCase.execute(any(), any(), any())) doReturn
                Single.just(DataWrapper.from(listOf(repository)))

        val viewModel = getViewModel()

        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")
        viewModel.previewRepository(repository)

        stateObservable.assertValueCount(4)

        eventsObservable.assertValueAt(
            0,
            SearchRepositoryViewModel.Event.FailedToOpenRepository
        )
    }

    @Test
    fun `check load more positive flow`() {
        val repositories1 = listOf(
            Repository(
                100,
                "123",
                null,
                "asdasd",
                1000,
                "123",
                "asdasd",
                false,
                100
            )
        )
        val repositories2 = listOf(
            Repository(
                100,
                "123",
                null,
                "asdasd",
                1000,
                "123",
                "asdasd",
                false,
                100
            )
        )

        whenever(searchUseCase.execute("123", 0, true)) doReturn
                Single.just(DataWrapper.from(repositories1))

        whenever(searchUseCase.execute("123", 1, true)) doReturn
                Single.just(DataWrapper.from(repositories2))

        val viewModel = getViewModel()
        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2)
        stateObservable
            .assertValueAt(
                4,
                SearchRepositoryViewModel.State(true, "123", repositories1, 0)
            )
            .assertValueAt(
                5,
                SearchRepositoryViewModel.State(true, "123", repositories1 + repositories2, 1)
            )
            .assertValueAt(
                6,
                SearchRepositoryViewModel.State(false, "123", repositories1 + repositories2, 1)
            )
        eventsObservable.assertNoValues()
            .assertNoErrors()

        verify(searchUseCase, times(1)).execute("123", 0, true)
        verify(searchUseCase, times(1)).execute("123", 1, true)
    }

    @Test
    fun `check load more negative flow -- failed to load second page`() {
        val repositories1 = listOf(
            Repository(
                100,
                "123",
                null,
                "asdasd",
                1000,
                "123",
                "asdasd",
                false,
                100
            )
        )
        whenever(searchUseCase.execute("123", 0, true)) doReturn
                Single.just(DataWrapper.from(repositories1))

        whenever(searchUseCase.execute("123", 1, true)) doReturn
                Single.just(DataWrapper.error(Exception()))

        val viewModel = getViewModel()
        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 + 1)

        stateObservable
            .assertValueAt(
                4,
                SearchRepositoryViewModel.State(true, "123", repositories1, 0)
            )
            .assertValueAt(
                5,
                SearchRepositoryViewModel.State(false, "123", repositories1, 0)
            )
        eventsObservable
            .assertValueAt(0, SearchRepositoryViewModel.Event.FailedToLoadRepositories)
            .assertNoErrors()

        verify(searchUseCase, times(1)).execute("123", 0, true)
        verify(searchUseCase, times(1)).execute("123", 1, true)
    }

    @Test
    fun `check load more negative flow -- not scrolled to download position`() {
        val repositories1 = listOf(
            Repository(
                100,
                "123",
                null,
                "asdasd",
                1000,
                "123",
                "asdasd",
                false,
                100
            )
        )
        whenever(searchUseCase.execute("123", 0, true)) doReturn
                Single.just(DataWrapper.from(repositories1))

        val viewModel = getViewModel()
        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 - 3)
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 - 2)
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 - 1)

        stateObservable.assertValueCount(4)
        eventsObservable
            .assertNoValues()
            .assertNoErrors()

        verify(searchUseCase, times(1)).execute("123", 0, true)
        verify(searchUseCase, times(0)).execute("123", 1, true)
    }

    @Test
    fun `check load more negative flow -- loading state`() {
        val repositories1 = listOf(
            Repository(
                100,
                "123",
                null,
                "asdasd",
                1000,
                "123",
                "asdasd",
                false,
                100
            )
        )
        whenever(searchUseCase.execute("123", 0, true)) doReturn
                Single.just(DataWrapper.from(repositories1))

        whenever(searchUseCase.execute("123", 1, true)) doReturn
                Single.never()

        val viewModel = getViewModel()
        val stateObservable = viewModel.toObservableState().test()
        val eventsObservable = viewModel.toObservableEvents().test()

        viewModel.newSearch("123")
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 - 3)
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 - 2)
        viewModel.loadMore(SEARCH_RESULTS_PER_PAGE / 2 - 1)

        stateObservable.assertValueCount(4)
        eventsObservable
            .assertNoValues()
            .assertNoErrors()

        verify(searchUseCase, times(1)).execute("123", 0, true)
        verify(searchUseCase, times(0)).execute("123", 1, true)
    }
}