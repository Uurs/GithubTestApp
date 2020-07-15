package bohdan.varchenko.domain.models

data class Repository(
    val id: Int,
    val name: String,
    val description: String,
    val htmlUrl: String,
    val ownerId: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val isViewed: Boolean
)