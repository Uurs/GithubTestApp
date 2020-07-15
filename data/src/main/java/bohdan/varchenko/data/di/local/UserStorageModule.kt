package bohdan.varchenko.data.di.local

import bohdan.varchenko.data.local.UserStorage
import bohdan.varchenko.data.local.storage.UserStorageImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UserStorageModule {

    @Binds
    fun bindsUserStorage(
        storageImpl: UserStorageImpl
    ): UserStorage
}