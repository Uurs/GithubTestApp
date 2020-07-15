package bohdan.varchenko.data.remote.dto


import com.fasterxml.jackson.annotation.JsonProperty

internal data class SearchResponseDto(
    @JsonProperty("incomplete_results")
    val incompleteResults: Boolean,
    @JsonProperty("items")
    val searchResponseItemDtos: List<SearchResponseItemDto>,
    @JsonProperty("total_count")
    val totalCount: Int
)

internal data class SearchResponseItemDto(
    @JsonProperty("created_at")
    val createdAt: String,
    @JsonProperty("default_branch")
    val defaultBranch: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("fork")
    val fork: Boolean,
    @JsonProperty("forks_count")
    val forksCount: Int,
    @JsonProperty("full_name")
    val fullName: String,
    @JsonProperty("homepage")
    val homepage: String,
    @JsonProperty("html_url")
    val htmlUrl: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("language")
    val language: String,
    @JsonProperty("master_branch")
    val masterBranch: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("node_id")
    val nodeId: String,
    @JsonProperty("open_issues_count")
    val openIssuesCount: Int,
    @JsonProperty("owner")
    val owner: SearchResponseOwnerDto,
    @JsonProperty("private")
    val `private`: Boolean,
    @JsonProperty("pushed_at")
    val pushedAt: String,
    @JsonProperty("score")
    val score: Double,
    @JsonProperty("size")
    val size: Int,
    @JsonProperty("stargazers_count")
    val stargazersCount: Int,
    @JsonProperty("updated_at")
    val updatedAt: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("watchers_count")
    val watchersCount: Int
)

internal data class SearchResponseOwnerDto(
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    @JsonProperty("gravatar_id")
    val gravatarId: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("login")
    val login: String,
    @JsonProperty("node_id")
    val nodeId: String,
    @JsonProperty("received_events_url")
    val receivedEventsUrl: String,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("url")
    val url: String
)