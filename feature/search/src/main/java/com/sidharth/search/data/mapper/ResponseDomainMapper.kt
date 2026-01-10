package com.sidharth.search.data.mapper

import com.sidharth.model.SearchItem
import com.sidharth.network.datasource.response.MovieListResponse

internal fun MovieListResponse.toDomain(): List<SearchItem> {
    if (results == null) {
        throw Exception("Invalid response")
    }

    return results!!.mapNotNull {
        if (it == null || it.id == null || it.title == null) {
            return@mapNotNull null
        }

        SearchItem(
            id = it.id ?: 0,
            title = it.title ?: "",
            posterUrl = "https://image.tmdb.org/t/p/w500${it.posterPath ?: ""}",
        )
    }
}