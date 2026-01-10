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

    override suspend fun getMovieDetails(movieId: Int): Flow<ResultState<Movie>> = flow {
        emit(ResultState.Loading)
        try {
            val response = remoteDataSource.fetchMovieDetails(movieId)
            emit(ResultState.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Something went wrong"))
        }
    }
}