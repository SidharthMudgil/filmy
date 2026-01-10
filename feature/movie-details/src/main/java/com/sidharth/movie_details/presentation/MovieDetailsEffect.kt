package com.sidharth.movie_details.presentation

sealed interface MovieDetailsEffect {
    object NavigateBack : MovieDetailsEffect
}
