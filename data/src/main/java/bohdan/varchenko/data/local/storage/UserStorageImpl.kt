package bohdan.varchenko.data.local.storage

import android.content.Context
import bohdan.varchenko.data.local.UserStorage
import bohdan.varchenko.data.utils.fromJson
import bohdan.varchenko.data.utils.toJsonString
import bohdan.varchenko.domain.models.User
import javax.inject.Inject

private const val USER_STORAGE_NAME = "user_storage"
private const val KEY_USER = "KEY_USER"

internal class UserStorageImpl
@Inject constructor(
    context: Context
) : UserStorage {
    private val preferences = context.getSharedPreferences(USER_STORAGE_NAME, Context.MODE_PRIVATE)

    override fun storeUser(user: User) {
        val valueToStore = user.toJsonString()
        preferences.edit()
            .putString(KEY_USER, valueToStore)
            .apply()
    }

    override fun getUser(): User? {
        return preferences.getString(KEY_USER, null)?.fromJson()
    }

    override fun clearUserData() {
        preferences.edit().remove(KEY_USER).apply()
    }
}