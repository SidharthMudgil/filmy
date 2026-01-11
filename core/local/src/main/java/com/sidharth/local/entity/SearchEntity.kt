package com.sidharth.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String,
)
