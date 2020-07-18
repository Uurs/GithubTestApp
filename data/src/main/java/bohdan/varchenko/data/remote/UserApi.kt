package bohdan.varchenko.data.remote

import bohdan.varchenko.data.remote.dto.UserResponseDto
import bohdan.varchenko.domain.models.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header

internal interface UserApi {

    @GET(ENDPOINT_USER)
    fun login(@Header("Authorization") token: String): Single<UserResponseDto>
}