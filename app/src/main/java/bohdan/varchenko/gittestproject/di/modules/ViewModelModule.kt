package bohdan.varchenko.gittestproject.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bohdan.varchenko.gittestproject.core.ViewModelFactory
import bohdan.varchenko.gittestproject.screens.home.HomeViewModel
import bohdan.varchenko.gittestproject.screens.profile.ProfileViewModel
import bohdan.varchenko.gittestproject.screens.recentsearchlist.RecentSearchViewModel
import bohdan.varchenko.gittestproject.screens.searchrepository.SearchRepositoryViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
internal interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchRepositoryViewModel::class)
    fun bindRepositoryListViewModel(viewModel: SearchRepositoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecentSearchViewModel::class)
    fun bindRecentSearchViewModel(viewModel: RecentSearchViewModel): ViewModel

    @MustBeDocumented
    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)
}