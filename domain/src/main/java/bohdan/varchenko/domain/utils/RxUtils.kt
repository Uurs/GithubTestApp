package bohdan.varchenko.domain.utils

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import io.reactivex.rxjava3.core.Observable

fun <T> Observable<DataWrapper<T>>.handleNoInternetConnection(
    internetObserver: InternetObserver
): Observable<DataWrapper<T>> {
    return onErrorResumeNext { th ->
        if (th is NoInternetConnection) {
            Observable.merge(
                Observable.just(DataWrapper.error(NoInternetConnection())),
                internetObserver.observeNetworkState()
                    .filter { it }
                    .flatMap { retry() }
            )

        } else {
            Observable.error(th)
        }
    }

}