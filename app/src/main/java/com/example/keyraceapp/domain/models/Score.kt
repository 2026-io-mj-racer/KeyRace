package com.example.keyraceapp.domain.models

import android.app.GameState

sealed class Score(val wpm: Float = 0f, val acc: Float = 0f) {
    data class TrainingScore(val correctWords: Int = 0, val mistakesMade: Int = 0): Score()
    data class ArcadeScore(val difficulty: Difficulty = Difficulty.EASY, val points : Long = 0): Score()
    companion object {
        fun buildScore(gameState: GameState, configState: ConfigState): Score{
            return ArcadeScore()
        }
    }
}

