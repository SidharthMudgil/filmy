package com.sidharth.search.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sidharth.model.SearchItem
import com.sidharth.search.presentation.SearchEffect
import com.sidharth.search.presentation.SearchIntent
import com.sidharth.search.presentation.SearchUiState
import com.sidharth.search.presentation.SearchViewModel

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateToMovieDetails -> {
                    // navController.navigate(...)
                }
            }
        }
    }

    SearchScreen(
        uiState = uiState,
        onQueryChange = {
            viewModel.onIntent(SearchIntent.UpdateQuery(it))
        },
        onRefresh = {
            viewModel.onIntent(SearchIntent.Refresh)
        },
        onMovieClicked = {
            viewModel.onIntent(SearchIntent.OpenMovieDetails(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onRefresh: () -> Unit,
    onMovieClicked: (Int) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            SearchBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(30.dp))

            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onRefresh = onRefresh,
                isRefreshing = uiState.isLoading,
                contentAlignment = Alignment.Center
            ) {
                when {
                    uiState.error != null -> {
                        Text(
                            text = uiState.error,
                            fontSize = 24.sp,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }

                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }

                    uiState.movies.isNotEmpty() -> {
                        SearchContent(
                            result = uiState.movies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SuccessSearchScreenPreview() {
    val searchItem1 = SearchItem(
        id = 21,
        title = "Avatar",
        posterUrl = "https://www.google.com/#q=posse",
    )

    val searchItem2 = SearchItem(
        id = 211,
        title = "Avatar",
        posterUrl = "https://www.google.com/#q=posse",
    )

    val searchItem3 = SearchItem(
        id = 212,
        title = "Avatar",
        posterUrl = "https://www.google.com/#q=posse",
    )

    SearchScreen(
        uiState = SearchUiState(
            movies = listOf(searchItem1, searchItem2, searchItem3)
        ),
        onMovieClicked = {},
        onRefresh = {},
        onQueryChange = {},
    )
}

@Preview
@Composable
private fun LoadingSearchScreenPreview() {
    SearchScreen(
        uiState = SearchUiState(isLoading = true),
        onMovieClicked = {},
        onRefresh = {},
        onQueryChange = {},
    )
}

@Preview
@Composable
private fun ErrorSearchScreenPreview() {
    SearchScreen(
        uiState = SearchUiState(error = "Unknown error"),
        onMovieClicked = {},
        onRefresh = {},
        onQueryChange = {},
    )
}