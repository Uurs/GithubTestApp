package bohdan.varchenko.data.mappers

import bohdan.varchenko.data.local.entities.RepositoryEntity
import bohdan.varchenko.data.remote.dto.SearchResponseItemDto
import bohdan.varchenko.domain.models.Repository

internal fun SearchResponseItemDto.toRepository(): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        htmlUrl = htmlUrl,
        ownerId = owner.id,
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        isViewed = false
    )

internal fun RepositoryEntity.toRepository(): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        htmlUrl = htmlUrl,
        ownerId = ownerId,
        ownerName = ownerName,
        ownerAvatarUrl = ownerAvatarUrl,
        isViewed = isViewed
    )