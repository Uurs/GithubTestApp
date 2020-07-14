package bohdan.varchenko.gittestproject

import android.app.Application
import bohdan.varchenko.gittestproject.di.AppComponent
import bohdan.varchenko.gittestproject.di.DaggerAppComponent
import bohdan.varchenko.gittestproject.di.modules.ApplicationContextModule

class Application : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationContextModule(ApplicationContextModule(this))
            .build()
    }
}