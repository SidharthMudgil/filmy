package com.sidharth.network.datasource.impl

import com.sidharth.network.datasource.ApiService
import com.sidharth.network.datasource.RemoteDataSource
import com.sidharth.network.datasource.response.MovieDetailsResponse
import com.sidharth.network.datasource.response.MovieListResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

internal class RemoteDataSourceImpl(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun fetchTrendingMovies(timeWindow: String): MovieListResponse = safeCall {
        apiService.fetchTrendingMovies(timeWindow)
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse = safeCall {
        apiService.fetchMovieDetails(movieId)
    }

    override suspend fun searchMovie(query: String): MovieListResponse = safeCall {
        apiService.searchMovie(query)
    }
}

private suspend fun <T> safeCall(apiCall: suspend () -> Response<T>): T {
    return try {
        val response = apiCall()

        if (!response.isSuccessful) {
            throw RuntimeException("API Error with error code: ${response.code()}")
        }

        if (response.body() == null) {
            throw RuntimeException("Response body is null")
        }

        response.body()!!
    } catch (_: IOException) {
        throw RuntimeException("Bad Network Connection or Timeout")
    } catch (e: HttpException) {
        throw RuntimeException("API Error with error code: ${e.code()}")
    } catch (_: Exception) {
        throw RuntimeException("Unknown error occurred")
    }
}