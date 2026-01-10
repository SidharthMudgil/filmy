package com.sidharth.network.datasource

import com.sidharth.network.datasource.response.MovieDetailsResponse
import com.sidharth.network.datasource.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    @GET("trending/movie/{time_window}")
    suspend fun fetchTrendingMovies(
        @Path("time_window") timeWindow: String,
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
    ): Response<MovieDetailsResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
    ): Response<MovieListResponse>
}