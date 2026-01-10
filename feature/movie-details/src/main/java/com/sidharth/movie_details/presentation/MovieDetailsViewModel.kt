package com.sidharth.movie_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.model.ResultState
import com.sidharth.movie_details.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _refreshTrigger = MutableStateFlow(Unit)

    val state: StateFlow<MovieDetailsUiState> = _refreshTrigger
        .flatMapLatest {
            getMovieDetailsUseCase(savedStateHandle["movieId"]!!)
        }
        .map { result ->
            when (result) {
                is ResultState.Loading -> MovieDetailsUiState(isLoading = true)
                is ResultState.Success -> MovieDetailsUiState(movie = result.data)
                is ResultState.Error -> MovieDetailsUiState(error = result.message)
                ResultState.Empty -> MovieDetailsUiState()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MovieDetailsUiState()
        )
    private val _effect = MutableSharedFlow<MovieDetailsEffect>()
    val effect: SharedFlow<MovieDetailsEffect> = _effect.asSharedFlow()

    fun onIntent(intent: MovieDetailsIntent) {
        when (intent) {
            MovieDetailsIntent.Refresh -> _refreshTrigger.value = Unit
            MovieDetailsIntent.BackClicked -> viewModelScope.launch {
                _effect.emit(MovieDetailsEffect.NavigateBack)
            }
        }
    }
}
