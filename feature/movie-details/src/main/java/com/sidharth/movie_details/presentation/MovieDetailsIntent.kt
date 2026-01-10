package com.sidharth.movie_details.presentation

internal sealed interface MovieDetailsIntent {
    object Refresh : MovieDetailsIntent
    object BackClicked : MovieDetailsIntent
}
