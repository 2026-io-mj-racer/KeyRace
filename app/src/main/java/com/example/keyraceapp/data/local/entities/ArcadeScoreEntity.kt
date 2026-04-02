package com.example.keyraceapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID


@Entity
data class ArcadeScoreEntity(
    @PrimaryKey val id: UUID? = null,
    val wpm: Float,
    val acc: Float,
    val difficulty: String,
    val points: Long,
    val date: Date


)

