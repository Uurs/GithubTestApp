package bohdan.varchenko.data.devicecontract

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import bohdan.varchenko.domain.devicecontract.InternetObserver
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

internal class AndroidInternetObserver @Inject constructor(
    private val context: Context
) : InternetObserver {
    override fun observeNetworkState(): Observable<Boolean> {
        val manager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)
            ?: return Observable.error(IllegalStateException("Can't access to connectivity manager"))
        val networkListener = NetworkListener()
        manager.addDefaultNetworkActiveListener(networkListener)
        return networkListener.subject
            .doOnDispose { manager.removeDefaultNetworkActiveListener(networkListener) }
    }

    private fun isNetworkAvailable(): Completable {
        return Completable.fromCallable {

        }
            .subscribeOn(Schedulers.io())
    }

    inner class NetworkListener : ConnectivityManager.OnNetworkActiveListener {
        val subject: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()

        override fun onNetworkActive() {
            isNetworkAvailable()
                .subscribe(
                    { subject.onNext(true) },
                    { subject.onNext(false) }
                )
        }
    }
}