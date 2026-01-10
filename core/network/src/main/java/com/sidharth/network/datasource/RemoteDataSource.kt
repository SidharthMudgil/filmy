package com.sidharth.network.datasource

import com.sidharth.network.datasource.response.MovieDetailsResponse
import com.sidharth.network.datasource.response.MovieSearchResponse
import com.sidharth.network.datasource.response.TrendingMoviesResponse

interface RemoteDataSource {
    suspend fun fetchTrendingMovies(timeWindow: String = "week"): TrendingMoviesResponse

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse

    suspend fun searchMovie(query: String): MovieSearchResponse
}
