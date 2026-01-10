package com.sidharth.search.domain.repository

import com.sidharth.model.ResultState
import com.sidharth.model.SearchItem
import kotlinx.coroutines.flow.Flow

internal interface SearchRepository {

    suspend fun searchMovie(query: String): Flow<ResultState<List<SearchItem>>>

    suspend fun getTrendingMovies(): Flow<ResultState<List<SearchItem>>>
}