package com.sidharth.movie_details.domain.usecase

import com.sidharth.model.Movie
import com.sidharth.model.ResultState
import com.sidharth.movie_details.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

internal class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<ResultState<Movie>> {
        return movieRepository.getMovieDetails(movieId)
    }
}