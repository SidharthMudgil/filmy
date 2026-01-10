package com.sidharth.movie_details.presentation

sealed interface MovieDetailsIntent {
    object Refresh : MovieDetailsIntent
    object BackClicked : MovieDetailsIntent
}
