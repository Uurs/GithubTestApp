package bohdan.varchenko.domain.usecases.impl.repository

import bohdan.varchenko.domain.BaseUseCaseTest
import bohdan.varchenko.domain.datasource.RepositoryDataSource
import bohdan.varchenko.domain.models.SearchQuery
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

internal class RepositoryRemoveRecentSearchUseCaseTest : BaseUseCaseTest() {

    @Test
    fun `positive flow`()  = block<Dto> {
        useCase.execute(SearchQuery(0, "name", 0))
            .test()
            .assertNoErrors()
        verify(dataSource, times(1)).deleteRecentSearch(any())

    }

    private data class Dto(
        val useCase: RepositoryRemoveRecentSearchUseCase,
        val dataSource: RepositoryDataSource
    )
}