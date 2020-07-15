package bohdan.varchenko.data.mappers

import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.domain.models.SearchQuery

internal fun SearchQuery.toSearchQueryEntity(): SearchQueryEntity =
    SearchQueryEntity(
        id = id,
        text = text,
        orderPosition = orderPosition
    )