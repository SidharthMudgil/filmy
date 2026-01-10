package com.sidharth.search.presentation

import com.sidharth.model.SearchItem

internal data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val movies: List<SearchItem> = emptyList(),
    val error: String? = null,
)
