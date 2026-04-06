package com.example.keyraceapp

import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.presentation.Game.ConfigState
import com.example.keyraceapp.presentation.Game.GameState
import org.junit.Test
import kotlin.test.assertEquals

class ScoreTest {

    @Test
    fun `buildScore() - returns ArcadeScore correctly based on ConfigState and GameState`() {
        val gameState = GameState(
            status = GameStatus.FINISHED,
            typedText = "ABCD EFG",
            targetText = "ABCD EFH",
            mistakesMade = 1,
            correctWords = 1,
            currentAcc = 0.86f,
            currentWpm = 50f
        )
        val configState = ConfigState(
            gameMode = GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS)
        )

        val expectedScore = Score.TrainingScore(
            correctWords = 1,
            mistakesMade = 1,
            wpm = 50f,
            acc = 0.86f,
        )


        val actualScore = Score.buildScore(gameState, configState)

        assertEquals(expectedScore, actualScore)
    }
}