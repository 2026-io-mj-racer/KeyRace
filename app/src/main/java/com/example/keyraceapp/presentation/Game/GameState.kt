package com.example.keyraceapp.presentation.Game

import com.example.keyraceapp.domain.models.GameStatus


data class GameState(
    val status: GameStatus,
    val lives: Int,
    val fallingSpeed: Float,
    val typedText: String,
    val targetText: String,
    val mistakesMade: Int,
    val correctWords: Int,
    val elapsedTime: Float,
    val currentWpm: Float,
    val currentAcc: Float,
    val points: Long

)

