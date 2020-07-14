package bohdan.varchenko.gittestproject.di.modules

import android.content.Context
import bohdan.varchenko.gittestproject.Application
import dagger.Module
import dagger.Provides

@Module
open class ApplicationContextModule(private val application: Application) {

    @Provides
    fun provideContext(): Context = application.applicationContext
}