package bohdan.varchenko.gittestproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import bohdan.varchenko.gittestproject.Application
import bohdan.varchenko.gittestproject.core.imageloader.ImageLoader
import bohdan.varchenko.gittestproject.di.AppComponent

abstract class BaseFragment<ViewModel : StatefulViewModel<*, *>> : Fragment() {

    protected lateinit var viewModel: ViewModel
    protected abstract val layoutResource: Int
    protected abstract val viewModelClass: Class<ViewModel>
    protected val imageLoader: ImageLoader by lazy {
        appComponent.getImageLoader()
    }
    private val appComponent: AppComponent
        get() = (requireActivity().application as Application).appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = appComponent.getViewModelFactory()
        viewModel = ViewModelProviders.of(requireActivity(), factory).get(viewModelClass)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }
}