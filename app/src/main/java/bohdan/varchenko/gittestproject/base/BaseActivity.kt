package bohdan.varchenko.gittestproject.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import bohdan.varchenko.gittestproject.Application
import bohdan.varchenko.gittestproject.core.imageloader.ImageLoader
import bohdan.varchenko.gittestproject.di.AppComponent

abstract class BaseActivity<ViewModel : StatefulViewModel<*, *>> : AppCompatActivity() {

    protected lateinit var viewModel: ViewModel
    protected abstract val layoutResource: Int
    protected abstract val viewModelClass: Class<ViewModel>
    protected val imageLoader: ImageLoader by lazy {
        appComponent.getImageLoader()
    }
    private val appComponent: AppComponent
        get() = (application as Application).appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        val factory = appComponent.getViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
    }
}