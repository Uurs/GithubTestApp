package bohdan.varchenko.domain.models

data class User(
    val id: String,
    val token: String,
    val name: String,
    val avatarUrl: String
)