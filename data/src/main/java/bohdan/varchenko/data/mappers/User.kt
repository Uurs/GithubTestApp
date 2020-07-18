package bohdan.varchenko.data.mappers

import bohdan.varchenko.data.remote.dto.UserResponseDto
import bohdan.varchenko.domain.models.User

fun UserResponseDto.toUser(
    token: String
): User = User(
    id = id,
    token = token,
    name = login,
    avatarUrl = avatar_url
)