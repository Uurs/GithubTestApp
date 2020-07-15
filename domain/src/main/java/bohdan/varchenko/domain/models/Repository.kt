package bohdan.varchenko.domain.models

data class Repository(
    val id: String,
    val name: String,
    val description: String,
    val htmlUrl: String,
    val ownerId: String,
    val ownerName: String,
    val ownerAvatarUrl: String
)