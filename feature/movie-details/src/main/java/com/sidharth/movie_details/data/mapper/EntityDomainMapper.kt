package com.sidharth.movie_details.data.mapper

import com.sidharth.local.entity.MovieEntity
import com.sidharth.model.Movie

internal fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        posterUrl = posterUrl,
        title = title,
        overview = overview,
    )
}

internal fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        posterUrl = posterUrl,
        title = title,
        overview = overview,
    )
}