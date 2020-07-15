package bohdan.varchenko.data.remote

import bohdan.varchenko.data.remote.dto.SearchResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RepositoryApi {

    @GET(ENDPOINT_SEARCH)
    fun search(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") orderBy: String
    ): Single<SearchResponseDto>
}