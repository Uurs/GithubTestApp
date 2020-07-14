package bohdan.varchenko.domain.utils

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import java.io.FileNotFoundException

class RxUtilsTest {

    @Test
    fun `check no internet connection util no internet connection occurred`() {
        val internetObserver = mock<InternetObserver>()
        whenever(internetObserver.observeNetworkState()) doReturn
                Observable.just(false, true)

        var i = 0
        Observable.create<DataWrapper<Int>> { emitter ->
            if (i == 0) emitter.onError(NoInternetConnection())
            else emitter.onNext(DataWrapper.from(i))
            i++
        }
            .let {
                it.handleNoInternetConnection(internetObserver)
            }
            .test()
            .awaitCount(2)
            .assertValueAt(0) { it.isEmpty() && it.error is NoInternetConnection }
            .assertValueAt(1, DataWrapper.from(1))
    }

    @Test
    fun `check no internet connection util another exception occurred`() {
        val internetObserver = mock<InternetObserver>()
        whenever(internetObserver.observeNetworkState()) doReturn
                Observable.just(false, true)

        var i = 0
        Observable.create<DataWrapper<Int>> { emitter ->
            if (i == 0) emitter.onError(FileNotFoundException())
            else emitter.onNext(DataWrapper.from(i))
            i++
        }
            .let {
                it.handleNoInternetConnection(internetObserver)
            }
            .test()
            .assertNoValues()
            .assertError(FileNotFoundException::class.java)
    }
}