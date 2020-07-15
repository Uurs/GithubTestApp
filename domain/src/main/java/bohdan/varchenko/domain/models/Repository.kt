package bohdan.varchenko.domain.models

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val htmlUrl: String,
    val ownerId: Long,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val isViewed: Boolean,
    val stars: Int
)