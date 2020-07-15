package bohdan.varchenko.domain.utils

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import io.reactivex.rxjava3.core.Single

fun <T> Single<DataWrapper<T>>.handleNoInternetConnection(
    internetObserver: InternetObserver
): Single<DataWrapper<T>> {
    return onErrorResumeNext { th ->
        if (th is NoInternetConnection) {
            internetObserver.observeNetworkState()
                .filter { it }
                .firstOrError()
                .flatMap { retry(1) }

        } else {
            Single.error(th)
        }
    }
}