package com.sidharth.movie_details.data.repository

import com.sidharth.model.Movie
import com.sidharth.model.ResultState
import com.sidharth.movie_details.data.mapper.toDomain
import com.sidharth.movie_details.domain.repository.MovieRepository
import com.sidharth.network.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    private val cache = mutableMapOf<Int, Movie>()

    override suspend fun getMovieDetails(movieId: Int): Flow<ResultState<Movie>> = flow {
        emit(ResultState.Loading)

        val cachedMovie = cache[movieId]
        try {
            cachedMovie?.let { emit(ResultState.Success(it)) }
            val response = remoteDataSource.fetchMovieDetails(movieId)
            emit(ResultState.Success(response.toDomain()))
        } catch (e: Exception) {
            cachedMovie?.let {
                emit(ResultState.Success(it))
            } ?: run {
                emit(ResultState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
}