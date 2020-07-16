package bohdan.varchenko.data.remote.dto


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class SearchResponseDto(
    @JsonProperty("incomplete_results")
    val incompleteResults: Boolean,
    @JsonProperty("items")
    val searchResponseItemDtos: List<SearchResponseItemDto>,
    @JsonProperty("total_count")
    val totalCount: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class SearchResponseItemDto(
    @JsonProperty("created_at") val createdAt: String,
    @JsonProperty("default_branch") val defaultBranch: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("fork") val fork: Boolean,
    @JsonProperty("forks_count") val forksCount: Int,
    @JsonProperty("full_name") val fullName: String,
    @JsonProperty("homepage") val homepage: String?,
    @JsonProperty("html_url") val htmlUrl: String,
    @JsonProperty("id") val id: Long,
    @JsonProperty("language") val language: String?,
    @JsonProperty("master_branch") val masterBranch: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("node_id") val nodeId: String,
    @JsonProperty("open_issues_count") val openIssuesCount: Int,
    @JsonProperty("owner") val owner: SearchResponseOwnerDto,
    @JsonProperty("private") val `private`: Boolean,
    @JsonProperty("pushed_at") val pushedAt: String,
    @JsonProperty("score") val score: Double,
    @JsonProperty("size") val size: Int,
    @JsonProperty("stargazers_count") val stargazersCount: Int,
    @JsonProperty("updated_at") val updatedAt: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("watchers_count") val watchersCount: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class SearchResponseOwnerDto(
    @JsonProperty("login") val login: String,
    @JsonProperty("id") val id: Long,
    @JsonProperty("node_id") val node_id: String,
    @JsonProperty("avatar_url") val avatar_url: String,
    @JsonProperty("gravatar_id") val gravatar_id: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("html_url") val html_url: String,
    @JsonProperty("followers_url") val followers_url: String,
    @JsonProperty("following_url") val following_url: String,
    @JsonProperty("gists_url") val gists_url: String,
    @JsonProperty("starred_url") val starred_url: String,
    @JsonProperty("subscriptions_url") val subscriptions_url: String,
    @JsonProperty("organizations_url") val organizations_url: String,
    @JsonProperty("repos_url") val repos_url: String,
    @JsonProperty("events_url") val events_url: String,
    @JsonProperty("received_events_url") val received_events_url: String,
    @JsonProperty("type") val type: String,
    @JsonProperty("site_admin") val site_admin: Boolean
)