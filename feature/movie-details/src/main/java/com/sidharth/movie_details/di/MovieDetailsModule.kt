package com.sidharth.movie_details.di

import com.sidharth.movie_details.data.repository.MovieRepositoryImpl
import com.sidharth.movie_details.domain.repository.MovieRepository
import com.sidharth.movie_details.domain.usecase.GetMovieDetailsUseCase
import com.sidharth.network.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MovieDetailsModule {

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: RemoteDataSource): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieRepository)
    }
}