package com.sidharth.local.di

import android.content.Context
import androidx.room.Room
import com.sidharth.local.AppDatabase
import com.sidharth.local.LocalConstants
import com.sidharth.local.LocalDataSource
import com.sidharth.local.MovieDao
import com.sidharth.local.impl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = LocalConstants.DATABASE
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideUserDataDao(
        appDatabase: AppDatabase
    ): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        movieDao: MovieDao
    ): LocalDataSource = LocalDataSourceImpl(
        movieDao = movieDao
    )
}