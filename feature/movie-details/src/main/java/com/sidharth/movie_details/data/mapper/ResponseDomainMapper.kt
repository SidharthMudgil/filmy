package com.sidharth.movie_details.data.mapper

import com.sidharth.model.Movie
import com.sidharth.model.SearchItem
import com.sidharth.network.datasource.response.MovieDetailsResponse
import com.sidharth.network.datasource.response.MovieListResponse

internal fun MovieDetailsResponse.toDomain(): Movie {
    if (id == null || title == null || overview == null) {
        throw Exception("Invalid response")
    }

    return Movie(
        id = id!!,
        title = title!!,
        posterUrl = "https://image.tmdb.org/t/p/w500${posterPath ?: ""}",
        overview = overview!!
    )
}