package bohdan.varchenko.gittestproject.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModelProviders: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as? T
            ?: throw IllegalArgumentException(
                "View Model $modelClass was not registered in ViewModelModule"
            )
    }
}