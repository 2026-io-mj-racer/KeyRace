package com.example.keyraceapp.presentation.Game.Arcade

import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameStatus

data class ArcadeState(
    val fallingWords: List<ArcadeWord> = listOf(
        ArcadeWord(10, 10, "first", speed = 200),
    ),
    val lives: Int = 3,
    val typedText: String = "",
    val currentTargetWord: String = "",
    val gameStatus: GameStatus? = null,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val elapsedTime: Long? = 0L,
    val currentWpm: Float = 0f,
    val points: Long = 0,
    val wholeTypedText: String = "",

    )
