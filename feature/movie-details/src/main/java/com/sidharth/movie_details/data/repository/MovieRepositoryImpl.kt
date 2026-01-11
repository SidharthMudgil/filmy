package com.sidharth.movie_details.data.repository

import com.sidharth.local.LocalDataSource
import com.sidharth.model.Movie
import com.sidharth.model.ResultState
import com.sidharth.movie_details.data.mapper.toDomain
import com.sidharth.movie_details.data.mapper.toEntity
import com.sidharth.movie_details.domain.repository.MovieRepository
import com.sidharth.network.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieRepository {

    override suspend fun getMovieDetails(movieId: Int): Flow<ResultState<Movie>> = flow {
        emit(ResultState.Loading)
        val cachedMovie = localDataSource.getMovieDetails(movieId)?.toDomain()
        cachedMovie?.let { emit(ResultState.Success(it)) }
        try {
            val response = remoteDataSource.fetchMovieDetails(movieId).toDomain()
            localDataSource.saveMovieDetails(response.toEntity())
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            cachedMovie?.let {
                emit(ResultState.Success(it))
            } ?: run {
                emit(ResultState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
}