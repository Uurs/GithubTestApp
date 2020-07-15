package bohdan.varchenko.data.mappers

import bohdan.varchenko.data.local.entities.SearchQueryEntity
import bohdan.varchenko.domain.models.SearchQuery

internal fun SearchQueryEntity.toSearchQuery(): SearchQuery =
    SearchQuery(
        id = id,
        text = text,
        orderPosition = orderPosition
    )