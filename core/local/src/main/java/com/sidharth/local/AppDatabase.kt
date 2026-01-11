package com.sidharth.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sidharth.local.entity.MovieEntity
import com.sidharth.local.entity.SearchEntity

@Database(
    entities = [MovieEntity::class, SearchEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}