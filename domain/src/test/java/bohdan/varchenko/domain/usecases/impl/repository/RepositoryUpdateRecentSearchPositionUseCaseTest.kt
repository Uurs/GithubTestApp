package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.SearchQuery
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Test

internal class RepositoryUpdateRecentSearchPositionUseCaseTest: BaseUseCaseTest() {

    @Test
    fun `positive flow`() = block<Dto> {
        useCase.execute(SearchQuery("#", 1), 2)
            .test()
            .assertNoErrors()

        verify(dataSource, times(1)).updateRecentSearch(SearchQuery("#", 2))
    }

    private data class Dto(
        val useCase: RepositoryUpdateRecentSearchPositionUseCase,
        val dataSource: RepositoryDataSource
    )
}