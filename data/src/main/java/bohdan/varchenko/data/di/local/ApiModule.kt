package bohdan.varchenko.data.di.local

import bohdan.varchenko.data.remote.RepositoryApi
import bohdan.varchenko.data.remote.UserApi
import bohdan.varchenko.domain.Requests
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
internal open class ApiModule {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Requests.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideRepositoryApi(): RepositoryApi {
        return retrofit.create(RepositoryApi::class.java)
    }
}