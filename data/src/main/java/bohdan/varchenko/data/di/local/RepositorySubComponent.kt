package bohdan.varchenko.data.di.local

import bohdan.varchenko.data.remote.UserApi
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ApiModule::class
    ]
)
abstract class RepositorySubComponent {

    internal abstract fun userApi(): UserApi

    @Subcomponent.Builder
    interface Builder{
        fun build(): RepositorySubComponent
    }
}