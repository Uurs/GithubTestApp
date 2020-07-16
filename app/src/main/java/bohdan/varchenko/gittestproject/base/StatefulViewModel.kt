package bohdan.varchenko.gittestproject.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class StatefulViewModel<State, Event> : BaseViewModel() {
    private val stateLiveData = MutableLiveData<State>()
    private val eventSubject = PublishSubject.create<Event>()
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
        eventSubject.subscribe { callback(it) }
            .cache()
    }

    protected fun putState(state: State) {
        stateLiveData.value = state
    }

    protected fun updateState(mapper: State.() -> State) {
        stateLiveData.value?.run {
            stateLiveData.postValue(this.mapper())
        }
    }

    protected fun postEvent(event: Event) {
        eventSubject.onNext(event)
    }

}