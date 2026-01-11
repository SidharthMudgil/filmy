package com.sidharth.local

import com.sidharth.local.entity.MovieEntity
import com.sidharth.local.entity.SearchEntity

interface LocalDataSource {

    suspend fun getTrendingMovies(): List<SearchEntity>

    suspend fun searchMovies(query: String): List<SearchEntity>

    suspend fun saveSearchResults(movies: List<SearchEntity>)

    suspend fun getMovieDetails(movieId: Int): MovieEntity?

    suspend fun saveMovieDetails(movie: MovieEntity)
}
