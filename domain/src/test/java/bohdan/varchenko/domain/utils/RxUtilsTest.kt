package bohdan.varchenko.domain.utils

import bohdan.varchenko.domain.DataWrapper
import bohdan.varchenko.domain.devicecontract.InternetObserver
import bohdan.varchenko.domain.exceptions.NoInternetConnection
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.io.FileNotFoundException

class RxUtilsTest {

    @Test
    fun `check no internet connection util no internet connection occurred`() {
        val internetObserver = mock<InternetObserver>()
        whenever(internetObserver.observeNetworkState()) doReturn
                Observable.just(false, true)

        var i = 0
        Single.create<DataWrapper<Int>> { emitter ->
            if (i++ == 0) emitter.onError(NoInternetConnection())
            else emitter.onSuccess(DataWrapper.from(i))
        }
            .handleNoInternetConnection(internetObserver)
            .test()
            .assertValueAt(0, DataWrapper.from(2))
            .assertNoErrors()
    }

    @Test
    fun `check no internet connection util another exception occurred`() {
        val internetObserver = mock<InternetObserver>()
        whenever(internetObserver.observeNetworkState()) doReturn
                Observable.just(false, true)

        var i = 0
        Single.create<DataWrapper<Int>> { emitter ->
            if (i++ == 0) emitter.onError(FileNotFoundException())
            else emitter.onSuccess(DataWrapper.from(i))
        }
            .handleNoInternetConnection(internetObserver)
            .test()
            .assertNoValues()
            .assertError(FileNotFoundException::class.java)
    }
}