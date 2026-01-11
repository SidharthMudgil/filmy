package com.sidharth.search.data.mapper

import com.sidharth.local.entity.SearchEntity
import com.sidharth.model.SearchItem

internal fun List<SearchEntity>.toDomain(): List<SearchItem> {
    return map {
        SearchItem(
            id = it.id,
            posterUrl = it.posterUrl,
            title = it.title,
        )
    }
}

internal fun List<SearchItem>.toEntity(): List<SearchEntity> {
    return map {
        SearchEntity(
            id = it.id,
            posterUrl = it.posterUrl,
            title = it.title,
        )
    }
}