package com.sidharth.search.presentation

internal sealed interface SearchIntent {
    data class UpdateQuery(val query: String) : SearchIntent
    object Refresh : SearchIntent
    data class OpenMovieDetails(val movieId: Int) : SearchIntent
}
