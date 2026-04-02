package com.example.keyraceapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class TrainingScoreEntity(
    @PrimaryKey val id: UUID? = null,
    val wpm: Float,
    val acc: Float,
    val correctWords: Int,
    val mistakesMade: Int,
    val trainingType: String,
    val date: Date
)
