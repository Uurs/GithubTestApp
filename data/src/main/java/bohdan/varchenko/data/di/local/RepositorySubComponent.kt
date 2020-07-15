package bohdan.varchenko.data.di.local

import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.UserApi
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ApiModule::class
    ]
)
abstract class RepositorySubComponent {

    internal abstract fun userApi(): UserApi

    internal abstract fun repositoryApi(): RepositoryApi

    @Subcomponent.Builder
    interface Builder{
        fun build(): RepositorySubComponent
    }
}