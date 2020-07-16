package bohdan.varchenko.gittestproject.screens

import android.content.Context
import bohdan.varchenko.gittestproject.screens.repositorypreview.RepositoryPreviewActivity

object Launcher {

    fun launchRepositoryDetailsActivity(
        context: Context,
        repositoryUrl: String
    ) {
        context.startActivity(RepositoryPreviewActivity.getIntent(context, repositoryUrl))
    }
}