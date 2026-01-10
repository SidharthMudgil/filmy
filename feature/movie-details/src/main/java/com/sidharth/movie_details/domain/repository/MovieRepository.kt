package com.sidharth.movie_details.domain.repository

import com.sidharth.model.Movie
import com.sidharth.model.ResultState
import com.sidharth.model.SearchItem
import kotlinx.coroutines.flow.Flow

internal interface MovieRepository {

    suspend fun getMovieDetails(movieId: Int): Flow<ResultState<Movie>>
}