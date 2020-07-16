package bohdan.varchenko.gittestproject.screens.repositorypreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import bohdan.varchenko.gittestproject.R
import bohdan.varchenko.gittestproject.utils.views.gone
import kotlinx.android.synthetic.main.activity_repository_preview.*

internal class RepositoryPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_preview)
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if (newProgress == 100) {
                    loading.gone()
                }
            }
        }
        webview.loadUrl(repositoryUrl)
    }

    companion object {
        private const val KEY_REPOSITORY_URL = "repository_url"
        private val RepositoryPreviewActivity.repositoryUrl: String
            get() = intent.getStringExtra(KEY_REPOSITORY_URL)
                ?: throw IllegalArgumentException(
                    "Activity launched in wrong way. Required fields not passed"
                )

        fun getIntent(context: Context, repositoryUrl: String): Intent =
            Intent(context, RepositoryPreviewActivity::class.java)
                .apply {
                    putExtra(KEY_REPOSITORY_URL, repositoryUrl)
                }
    }
}