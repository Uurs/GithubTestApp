package bohdan.varchenko.gittestproject.screens

import android.content.Context
import bohdan.varchenko.domain.models.SearchQuery
import bohdan.varchenko.gittestproject.screens.repositorypreview.RepositoryPreviewActivity
import bohdan.varchenko.gittestproject.screens.searchrepository.SearchRepositoryActivity

object Launcher {

    fun launchRepositoryDetailsActivity(
        context: Context,
        repositoryUrl: String
    ) {
        context.startActivity(RepositoryPreviewActivity.getIntent(context, repositoryUrl))
    }

    fun launchSearchRepositoryActivity(
        context: Context,
        query: SearchQuery?
    ) {
        context.startActivity(SearchRepositoryActivity.getIntent(context, query))
    }
}