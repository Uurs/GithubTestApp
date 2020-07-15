package bohdan.varchenko.data.remote

import bohdan.varchenko.domain.models.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface UserApi {

    @GET("/user")
    fun login(name: String, password: String): Single<User>
}