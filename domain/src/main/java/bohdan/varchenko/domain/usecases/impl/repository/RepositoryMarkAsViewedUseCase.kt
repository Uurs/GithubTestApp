package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.Repository
import bohdan.varchenko.domain.usecases.RepositoryUseCase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class RepositoryMarkAsViewedUseCase
@Inject constructor(
    private val dataSource: RepositoryDataSource
): RepositoryUseCase.MarkAsViewed {

    override fun execute(repository: Repository): Completable {
        return dataSource.markAsRead(repositoryId = repository.id)
    }
}