package bohdan.varchenko.domain

object SearchConfig {
    const val SEARCH_RESULTS_PER_PAGE = 30
    const val MAX_THREAD_COUNT = 2
    const val DEFAULT_SORT_BY = "stars"

    const val ORDER_DESC = "desc"
    const val ORDER_ASC = "asc"
}

object Requests {
    const val BASE_URL = "https://api.github.com"
}