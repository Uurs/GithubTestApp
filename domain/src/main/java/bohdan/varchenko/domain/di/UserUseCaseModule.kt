package bohdan.varchenko.domain.di

import bohdan.varchenko.domain.datasource.UserDataSource
import bohdan.varchenko.domain.usecases.UserUseCase
import bohdan.varchenko.domain.usecases.impl.user.GetCurrentUserUseCase
import bohdan.varchenko.domain.usecases.impl.user.UserLoginUseCase
import bohdan.varchenko.domain.usecases.impl.user.UserLogoutUseCase
import dagger.Module
import dagger.Provides

@Module
open class UserUseCaseModule {

    @Provides
    fun providesLogin(dataSource: UserDataSource): UserUseCase.Login =
        UserLoginUseCase(dataSource)

    @Provides
    fun provideLogout(dataSource: UserDataSource): UserUseCase.Logout =
        UserLogoutUseCase(dataSource)

    @Provides
    fun providesGetCurrentUser(dataSource: UserDataSource): UserUseCase.GetCurrentUser =
        GetCurrentUserUseCase(dataSource)
}