package com.sidharth.search.domain.usecase

import com.sidharth.model.ResultState
import com.sidharth.model.SearchItem
import com.sidharth.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchMovieUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Flow<ResultState<List<SearchItem>>> {
        return searchRepository.searchMovie(query)
    }
}