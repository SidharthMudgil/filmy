package com.sidharth.network.datasource.di

import com.sidharth.network.datasource.ApiService
import com.sidharth.network.datasource.NetworkConstants
import com.sidharth.network.datasource.RemoteDataSource
import com.sidharth.network.datasource.impl.RemoteDataSourceImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.sidharth.network.BuildConfig
import dagger.Module

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder().url(request.url)
                 .addHeader("Authorization", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }


        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }
}