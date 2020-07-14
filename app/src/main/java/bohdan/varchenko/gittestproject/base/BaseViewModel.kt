package bohdan.varchenko.gittestproject.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val onCleanDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        onCleanDisposable.dispose()
    }

    protected fun Disposable.cache(): Disposable =
        apply { onCleanDisposable.add(this) }
}