package bohdan.varchenko.data.repositories

import bohdan.varchenko.data.local.UserStorage
import bohdan.varchenko.data.remote.UserApi
import bohdan.varchenko.domain.exceptions.NotAuthorizedException
import bohdan.varchenko.domain.models.User
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Test

class UserDataSourceImplTest {
    private val userApi: UserApi = mock()
    private val userStorage: UserStorage = mock()

    private val dataSource: UserDataSourceImpl
        get() = UserDataSourceImpl(userApi, userStorage)

    @Test
    fun `test login positive flow`() {
        whenever(userApi.login(any(), any())) doReturn Single.just(User("123", "123", "123", "123"))
        dataSource.login("123", "123")
            .test()
            .assertNoErrors()

        verify(userApi, times(1)).login(any(), any())
        verify(userStorage, times(1)).storeUser(any())
    }

    @Test
    fun `test logout positive flow`() {
        dataSource.logout()
            .test()
            .assertNoErrors()

        verify(userStorage, times(1)).clearUserData()
        verifyZeroInteractions(userApi)
    }

    @Test
    fun `test get current user -- positive flow`() {
        whenever(userStorage.getUser()) doReturn User("123", "123", "123", "123")
        dataSource.getCurrentUser()
            .test()
            .assertNoErrors()

        verify(userStorage, times(1)).getUser()
        verifyZeroInteractions(userApi)
    }

    @Test
    fun `test get current user -- negative flow user not logged in`() {
        whenever(userStorage.getUser()) doReturn null
        dataSource.getCurrentUser()
            .test()
            .assertError(NotAuthorizedException::class.java)

        verify(userStorage, times(1)).getUser()
        verifyZeroInteractions(userApi)
    }
}