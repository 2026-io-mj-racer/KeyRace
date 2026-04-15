package com.example.keyraceapp.domain.models

import com.example.keyraceapp.presentation.Game.ConfigState
import com.example.keyraceapp.presentation.Game.GameState

sealed class Score()  {
    abstract val wpm: Float
    abstract val acc: Float
    data class TrainingScore(
        val correctWords: Int = 0,
        override val wpm: Float = 0f,
        override val acc: Float = 0f,
        val mistakesMade: Int = 0,
        val trainingType: String = "TIME_BASED"
        ): Score()
    data class ArcadeScore (
        override val wpm: Float = 0f,
        override val acc: Float = 0f,
        val difficulty: Difficulty = Difficulty.EASY,
        val points : Long = 0
    ): Score()

    companion object {
        fun buildScore(gameState: GameState , configState: ConfigState): Score{
            return ArcadeScore()
        }
    }
}

