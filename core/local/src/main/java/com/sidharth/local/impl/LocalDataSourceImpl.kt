package com.sidharth.local.impl

import com.sidharth.local.LocalDataSource
import com.sidharth.local.MovieDao
import com.sidharth.local.entity.MovieEntity
import com.sidharth.local.entity.SearchEntity
import javax.inject.Inject

internal class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : LocalDataSource {

    override suspend fun getTrendingMovies(): List<SearchEntity> {
        return movieDao.getTrendingMovies()
    }

    override suspend fun searchMovies(query: String): List<SearchEntity> {
        return movieDao.searchMoviesByTitle(query)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity? {
        return movieDao.getMovieById(movieId)
    }

    override suspend fun saveSearchResults(movies: List<SearchEntity>) {
        movieDao.upsertMovies(movies)
    }

    override suspend fun saveMovieDetails(movie: MovieEntity) {
        movieDao.upsertMovie(movie)
    }
}
