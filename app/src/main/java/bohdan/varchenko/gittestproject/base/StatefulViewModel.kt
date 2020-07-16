package bohdan.varchenko.gittestproject.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class StatefulViewModel<State, Event> : BaseViewModel() {
    protected val stateLiveData = MutableLiveData<State>()
    private val eventSubject = PublishSubject.create<Event>()
    private var eventsDisposable: Disposable? = null
    protected val state: State
        get() = stateLiveData.value!!

    fun subscribeForState(
        lifecycleOwner: LifecycleOwner,
        callback: (State) -> Unit
    ) {
        stateLiveData.observe(lifecycleOwner, Observer<State> { callback(it) })
    }

    fun subscribeForEvents(
        lifecycleOwner: LifecycleOwner,
        callback: (Event) -> Unit
    ) {
        eventsDisposable?.dispose()
        eventsDisposable = eventSubject.subscribe { callback(it) }
            .cache()
    }

    protected fun putState(state: State) {
        stateLiveData.value = state
    }

    protected inline fun updateState(mapper: State.() -> State) {
        stateLiveData.value?.run {
            stateLiveData.value = this.mapper()
        }
    }

    protected fun postEvent(event: Event) {
        eventSubject.onNext(event)
    }

}