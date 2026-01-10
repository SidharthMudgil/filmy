package com.sidharth.search.presentation

internal sealed interface SearchEffect {
    data class NavigateToMovieDetails(val movieId: Int) : SearchEffect
}
