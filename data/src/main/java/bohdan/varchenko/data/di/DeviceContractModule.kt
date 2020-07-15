package bohdan.varchenko.data.di

import android.content.Context
import bohdan.varchenko.data.devicecontract.AndroidInternetObserver
import bohdan.varchenko.domain.devicecontract.InternetObserver
import dagger.Module
import dagger.Provides

@Module
open class DeviceContractModule {

    @Provides
    fun provideInternetObserver(context: Context): InternetObserver {
        return AndroidInternetObserver(context)
    }
}