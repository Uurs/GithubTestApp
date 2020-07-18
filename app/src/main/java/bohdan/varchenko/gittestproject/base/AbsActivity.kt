package bohdan.varchenko.gittestproject.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bohdan.varchenko.gittestproject.Application
import bohdan.varchenko.gittestproject.core.imageloader.ImageLoader
import bohdan.varchenko.gittestproject.di.AppComponent

abstract class AbsActivity : AppCompatActivity() {
    protected abstract val layoutResource: Int
    protected val imageLoader: ImageLoader by lazy {
        appComponent.getImageLoader()
    }
    protected val appComponent: AppComponent
        get() = (application as Application).appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
    }
}