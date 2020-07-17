package bohdan.varchenko.gittestproject.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<ViewModel : StatefulViewModel<*, *>> : AbsActivity() {

    protected lateinit var viewModel: ViewModel
    protected abstract val viewModelClass: Class<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = appComponent.getViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
    }
}