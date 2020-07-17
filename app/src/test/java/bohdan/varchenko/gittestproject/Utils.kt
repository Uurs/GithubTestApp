package bohdan.varchenko.gittestproject

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import bohdan.varchenko.gittestproject.base.StatefulViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable

fun <STATE> StatefulViewModel<STATE, *>.toObservableState(): Observable<STATE> {
    return Observable.create { emitter ->
        val lifecycleOwner = mock<LifecycleOwner>()
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        whenever(lifecycleOwner.getLifecycle()) doReturn lifecycle
        subscribeForState(lifecycleOwner) { emitter.onNext(it) }
    }

}fun <EVENTS> StatefulViewModel<*, EVENTS>.toObservableEvents(): Observable<EVENTS> {
    return Observable.create { emitter ->
        val lifecycleOwner = mock<LifecycleOwner>()
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        whenever(lifecycleOwner.getLifecycle()) doReturn lifecycle
        subscribeForEvents(lifecycleOwner) { emitter.onNext(it) }
    }
}