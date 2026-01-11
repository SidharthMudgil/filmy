package com.sidharth.search.di

import com.sidharth.local.LocalDataSource
import com.sidharth.network.datasource.RemoteDataSource
import com.sidharth.search.data.repository.SearchRepositoryImpl
import com.sidharth.search.domain.repository.SearchRepository
import com.sidharth.search.domain.usecase.GetTrendingMoviesUseCase
import com.sidharth.search.domain.usecase.SearchMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SearchModule {

    @Singleton
    @Provides
    fun provideSearchRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }

    @Singleton
    @Provides
    fun provideSearchUseCase(searchRepository: SearchRepository): SearchMovieUseCase {
        return SearchMovieUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun provideGetTrendingMoviesUseCase(searchRepository: SearchRepository): GetTrendingMoviesUseCase {
        return GetTrendingMoviesUseCase(searchRepository)
    }
}