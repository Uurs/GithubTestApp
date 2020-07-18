package bohdan.varchenko.data.remote.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResponseDto(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("login")
    val login: String,
    @JsonProperty("avatar_url")
    val avatar_url: String
)