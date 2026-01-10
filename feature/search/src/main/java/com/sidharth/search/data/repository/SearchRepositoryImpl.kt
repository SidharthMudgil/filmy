package com.sidharth.search.data.repository

import com.sidharth.model.ResultState
import com.sidharth.model.SearchItem
import com.sidharth.network.datasource.RemoteDataSource
import com.sidharth.search.data.mapper.toDomain
import com.sidharth.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): SearchRepository {
    override suspend fun searchMovie(query: String): Flow<ResultState<List<SearchItem>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = remoteDataSource.searchMovie(query)
            emit(ResultState.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Something went wrong"))
        }
    }

    override suspend fun getTrendingMovies(): Flow<ResultState<List<SearchItem>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = remoteDataSource.fetchTrendingMovies()
            emit(ResultState.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Something went wrong"))
        }
    }
}