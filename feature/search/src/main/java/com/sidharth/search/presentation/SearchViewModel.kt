@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.sidharth.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.model.ResultState
import com.sidharth.search.domain.usecase.GetTrendingMoviesUseCase
import com.sidharth.search.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    private val _refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val state: StateFlow<SearchUiState> = combine(
        _query.debounce(300).distinctUntilChanged(),
        _refreshTrigger.onStart { emit(Unit) }
    ) { query, _ -> query }
        .flatMapLatest { query ->
            val flow = if (query.isBlank()) {
                getTrendingMoviesUseCase()
            } else {
                searchMovieUseCase(query)
            }
            flow.map { result -> query to result }
        }
        .map { (query, result) ->
            when (result) {
                is ResultState.Loading -> SearchUiState(isLoading = true, query = query)
                is ResultState.Success -> SearchUiState(movies = result.data, query = query)
                is ResultState.Error -> SearchUiState(error = result.message, query = query)
                ResultState.Empty -> SearchUiState(isLoading = false, query = query)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState()
        )

    private val _effect = MutableSharedFlow<SearchEffect>()
    val effect: SharedFlow<SearchEffect> = _effect.asSharedFlow()

    fun onIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.UpdateQuery -> _query.update { intent.query }
            SearchIntent.Refresh -> _refreshTrigger.tryEmit(Unit)
            is SearchIntent.OpenMovieDetails -> viewModelScope.launch {
                _effect.emit(SearchEffect.NavigateToMovieDetails(intent.movieId))
            }
        }
    }
}

