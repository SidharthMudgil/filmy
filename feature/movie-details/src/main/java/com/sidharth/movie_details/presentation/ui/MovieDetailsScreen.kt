package com.sidharth.movie_details.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sidharth.model.Movie
import com.sidharth.movie_details.presentation.MovieDetailsEffect
import com.sidharth.movie_details.presentation.MovieDetailsIntent
import com.sidharth.movie_details.presentation.MovieDetailsUiState
import com.sidharth.movie_details.presentation.MovieDetailsViewModel
import com.sidharth.ui.R

@Composable
internal fun MovieDetailsScreen(viewModel: MovieDetailsViewModel) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MovieDetailsEffect.NavigateBack -> {

                }
            }
        }
    }

    MovieDetailsScreen(
        uiState = uiState,
        onBackClicked = {
            viewModel.onIntent(MovieDetailsIntent.BackClicked)
        },
        onRefresh = {
            viewModel.onIntent(MovieDetailsIntent.Refresh)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsScreen(
    uiState: MovieDetailsUiState,
    onBackClicked: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(paddingValues),
            onRefresh = onRefresh,
            isRefreshing = uiState.isLoading,
            contentAlignment = Alignment.Center,
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

                uiState.movie != null -> {
                    MovieDetailsContent(
                        movie = uiState.movie,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    )
                }

                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Preview
@Composable
private fun SuccessMovieDetailsScreenPreview() {
    val dummyMovie = Movie(
        id = 21,
        title = "latine",
        overview = "equidem",
        posterUrl = "https://www.google.com/#q=posse",
    )

    MovieDetailsScreen(
        uiState = MovieDetailsUiState(movie = dummyMovie),
        onBackClicked = {},
        onRefresh = {}
    )
}

@Preview
@Composable
private fun LoadingMovieDetailsScreenPreview() {
    MovieDetailsScreen(
        uiState = MovieDetailsUiState(isLoading = true),
        onBackClicked = {},
        onRefresh = {}
    )
}

@Preview
@Composable
private fun ErrorMovieDetailsScreenPreview() {
    MovieDetailsScreen(
        uiState = MovieDetailsUiState(error = "Unknown error"),
        onBackClicked = {},
        onRefresh = {}
    )
}