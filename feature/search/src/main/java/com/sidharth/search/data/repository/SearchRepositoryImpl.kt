package com.sidharth.search.data.repository

import android.util.Log.e
import com.sidharth.local.LocalDataSource
import com.sidharth.model.ResultState
import com.sidharth.model.SearchItem
import com.sidharth.network.datasource.RemoteDataSource
import com.sidharth.search.data.mapper.toDomain
import com.sidharth.search.data.mapper.toEntity
import com.sidharth.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : SearchRepository {

    override suspend fun searchMovie(query: String): Flow<ResultState<List<SearchItem>>> = flow {
        emit(ResultState.Loading)
        val cachedResult = localDataSource.searchMovies(query).toDomain().takeIf { it.isNotEmpty() }
        cachedResult?.let { emit(ResultState.Success(it)) }
        try {
            val response = remoteDataSource.searchMovie(query).toDomain()
            if (response.isNotEmpty()) {
                localDataSource.saveSearchResults(response.toEntity())
                emit(ResultState.Success(response))
            } else {
                throw Exception("No results found")
            }
        } catch (e: Exception) {
            if (cachedResult.isNullOrEmpty()) {
                emit(ResultState.Error(e.message ?: "Something went wrong"))
            } else {
                emit(ResultState.Success(cachedResult))
            }
        }
    }

    override suspend fun getTrendingMovies(): Flow<ResultState<List<SearchItem>>> = flow {
        emit(ResultState.Loading)
        val trendingCache = localDataSource.getTrendingMovies().toDomain().takeIf { it.isNotEmpty() }
        trendingCache?.let { emit(ResultState.Success(it)) }
        try {
            val response = remoteDataSource.fetchTrendingMovies().toDomain()
            if (response.isNotEmpty()) {
                localDataSource.saveSearchResults(response.toEntity())
                emit(ResultState.Success(response))
            } else {
                throw Exception("No results found")
            }
        } catch (e: Exception) {
            if (trendingCache.isNullOrEmpty()) {
                emit(ResultState.Error(e.message ?: "Something went wrong"))
            } else {
                emit(ResultState.Success(trendingCache))
            }
        }
    }
}