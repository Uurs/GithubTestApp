package bohdan.varchenko.gittestproject.di

import androidx.lifecycle.ViewModelProvider
import bohdan.varchenko.data.di.DataSourceModule
import bohdan.varchenko.data.di.DeviceContractModule
import bohdan.varchenko.domain.di.RepositoryUseCaseModule
import bohdan.varchenko.domain.di.UserUseCaseModule
import bohdan.varchenko.gittestproject.core.imageloader.ImageLoader
import bohdan.varchenko.gittestproject.di.modules.ApplicationContextModule
import bohdan.varchenko.gittestproject.di.modules.ImageLoaderModule
import bohdan.varchenko.gittestproject.di.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UserUseCaseModule::class,
        RepositoryUseCaseModule::class,
        DeviceContractModule::class,
        ApplicationContextModule::class,
        ViewModelModule::class,
        DataSourceModule::class,
        ImageLoaderModule::class
    ]
)
interface AppComponent {

    fun getViewModelFactory(): ViewModelProvider.Factory

    fun getImageLoader(): ImageLoader
}