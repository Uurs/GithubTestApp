package bohdan.varchenko.gittestproject.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import bohdan.varchenko.gittestproject.Application

abstract class BaseActivity<ViewModel : StatefulViewModel<*, *>> : AppCompatActivity() {

    protected lateinit var viewModel: ViewModel
    protected abstract val layoutResource: Int
    protected abstract val viewModelClass: Class<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        val factory = (application as Application).appComponent.getViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
    }
}