package com.sidharth.movie_details.presentation

internal sealed interface MovieDetailsEffect {
    object NavigateBack : MovieDetailsEffect
}
