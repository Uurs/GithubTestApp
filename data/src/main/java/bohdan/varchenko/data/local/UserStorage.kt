package bohdan.varchenko.data.local

import bohdan.varchenko.domain.models.User

internal interface UserStorage {

    fun storeUser(user: User)

    fun getUser(): User?

    fun clearUserData()
}