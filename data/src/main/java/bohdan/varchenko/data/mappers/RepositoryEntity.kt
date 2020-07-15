package bohdan.varchenko.data.mappers

import bohdan.varchenko.data.local.entities.RepositoryEntity
import bohdan.varchenko.domain.models.Repository

internal fun Repository.toRepositoryEntity(): RepositoryEntity =
    RepositoryEntity(
        id = id,
        name = name,
        description = description,
        htmlUrl = htmlUrl,
        ownerId = ownerId,
        ownerName = ownerName,
        ownerAvatarUrl = ownerAvatarUrl,
        isViewed = isViewed,
        stars = stars.toLong()
    )