package com.example.keyraceapp.domain.models

import com.example.keyraceapp.presentation.Game.ConfigState
import com.example.keyraceapp.presentation.Game.Training.GameState

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
            return when(configState.gameMode) {
                is GameMode.Arcade ->
                    ArcadeScore(
                        wpm = gameState.currentWpm ?: 0.0f,
                        acc = gameState.currentAcc ?: 0.0f,
                        points = gameState.points ?: 0L,
                        difficulty = configState.gameMode.difficulty
                    )
                is GameMode.Training ->
                    TrainingScore(
                        wpm = gameState.currentWpm ?: 0.0f,
                        acc = gameState.currentAcc ?: 0.0f,
                        trainingType = when(configState.gameMode) {
                            is GameMode.Training.TimeBased -> "TIME_BASED"
                            is GameMode.Training.WordBased -> "WORD_BASED"
                        },
                            mistakesMade = gameState.mistakesMade ?: 0,
                            correctWords = gameState.correctWords ?: 0,
                        )
                else -> throw IllegalStateException("""
                    configState.gameMode is null!
                    Please make sure that you pass valid configState to the builder.
                """.trimIndent())
            }
        }
    }
}

