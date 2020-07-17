package bohdan.varchenko.gittestproject.screens.searchrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import bohdan.varchenko.gittestproject.toObservableEvents
import bohdan.varchenko.gittestproject.toObservableState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchRepositoryViewModelTest {
    private val searchUseCase: RepositoryUseCase.Search = mock()
    private val markAsViewedUseCase: RepositoryUseCase.MarkAsViewed = mock()

    private fun getViewModel(): SearchRepositoryViewModel = SearchRepositoryViewModel(
        searchUseCase = searchUseCase,
        markAsViewedUseCase = markAsViewedUseCase
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

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
}