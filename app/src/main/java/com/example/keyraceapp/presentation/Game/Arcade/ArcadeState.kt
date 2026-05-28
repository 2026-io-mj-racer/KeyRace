package com.example.keyraceapp.presentation.Game.Arcade

import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameStatus

data class ArcadeState(
    val fallingWords: List<ArcadeWord> = emptyList(),
    val lives: Int = 3,
    val typedText: String = "",
    val currentTargetWord: String = "",
    val gameStatus: GameStatus? = null,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val points: Long = 0,
    val wholeTypedText: String = "",
    val errorMsg: String? = null,
    val isLoading: Boolean = false

    )
