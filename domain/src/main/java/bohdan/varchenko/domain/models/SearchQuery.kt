package bohdan.varchenko.domain.models

data class SearchQuery(
    val id: Long,
    val text: String,
    val orderPosition: Int
)