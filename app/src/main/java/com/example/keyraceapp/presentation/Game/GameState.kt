package com.example.keyraceapp.presentation.Game

import com.example.keyraceapp.domain.models.GameStatus


data class GameState(
    val status: GameStatus? = null,
    val lives: Int?  = null,
    val fallingSpeed: Float? = null,
    val typedText: String = "",
    val currentWordBox: Int = 0,
    val lenOverall: Long? = null,
    val allWords: List<String>? = null,
    val mistakesMade: Int? = null,
    val correctWords: Int? = null,
    val elapsedTime: Long? = null,
    val timeBeforePauses: Long = 0L,
    val currentWpm: Float? = null,
    val currentAcc: Float? = null,
    val points: Long? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null


)

