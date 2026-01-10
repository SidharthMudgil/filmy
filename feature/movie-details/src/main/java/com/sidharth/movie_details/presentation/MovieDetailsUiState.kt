package com.sidharth.movie_details.presentation

import com.sidharth.model.Movie

internal data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String? = null
)
