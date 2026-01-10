package com.sidharth.network.datasource

import com.sidharth.network.datasource.response.MovieDetailsResponse
import com.sidharth.network.datasource.response.MovieListResponse

interface RemoteDataSource {
    suspend fun fetchTrendingMovies(timeWindow: String = "week"): MovieListResponse

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse

    suspend fun searchMovie(query: String): MovieListResponse
}
