package com.sidharth.search.data.repository

import com.sidharth.model.ResultState
import com.sidharth.model.SearchItem
import com.sidharth.network.datasource.RemoteDataSource
import com.sidharth.search.data.mapper.toDomain
import com.sidharth.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {
    private val searchCache = mutableMapOf<String, List<SearchItem>>()
    private val trendingCache = mutableListOf<SearchItem>()

    override suspend fun searchMovie(query: String): Flow<ResultState<List<SearchItem>>> = flow {
        emit(ResultState.Loading)
        val cachedResult = searchCache[query]
        try {
            cachedResult?.let { emit(ResultState.Success(it)) }
            val response = remoteDataSource.searchMovie(query)
            emit(ResultState.Success(response.toDomain()))
        } catch (e: Exception) {
            cachedResult?.let {
                emit(ResultState.Success(it))
            } ?: run {
                emit(ResultState.Error(e.message ?: "Something went wrong"))
            }
        }
    }

    override suspend fun getTrendingMovies(): Flow<ResultState<List<SearchItem>>> = flow {
        emit(ResultState.Loading)
        try {
            emit(ResultState.Success(trendingCache))
            val response = remoteDataSource.fetchTrendingMovies()
            emit(ResultState.Success(response.toDomain()))
        } catch (e: Exception) {
            if (trendingCache.isNotEmpty()) {
                emit(ResultState.Success(trendingCache))
            } else {
                emit(ResultState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
}