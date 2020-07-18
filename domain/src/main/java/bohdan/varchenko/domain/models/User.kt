package bohdan.varchenko.domain.models


data class User(
    val id: Long,
    val token: String,
    val name: String,
    val avatarUrl: String
)