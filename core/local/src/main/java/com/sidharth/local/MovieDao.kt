package com.sidharth.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sidharth.local.entity.MovieEntity
import com.sidharth.local.entity.SearchEntity

@Dao
internal interface MovieDao {

    @Query("SELECT * FROM SearchEntity")
    suspend fun getTrendingMovies(): List<SearchEntity>

    @Query("""
        SELECT * FROM SearchEntity 
        WHERE title LIKE '%' || :query || '%' COLLATE NOCASE
    """)
    suspend fun searchMoviesByTitle(query: String): List<SearchEntity>

    @Upsert
    suspend fun upsertMovies(movies: List<SearchEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Upsert
    suspend fun upsertMovie(movie: MovieEntity)
}
