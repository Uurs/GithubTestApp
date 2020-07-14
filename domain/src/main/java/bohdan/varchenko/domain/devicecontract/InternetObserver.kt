package bohdan.varchenko.domain.devicecontract

import io.reactivex.rxjava3.core.Observable

interface InternetObserver {

    fun observeNetworkState(): Observable<Boolean>
}