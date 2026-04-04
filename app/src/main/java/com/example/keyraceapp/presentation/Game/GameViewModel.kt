package com.example.keyraceapp.presentation.Game

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.TimeProvider
import kotlinx.coroutines.Job
import kotlin.time.Clock


class GameViewModel(
    private val wordRepository: WordRepository,
    private val scoreRepository: ScoreRepository,
    private  val timeProvider: TimeProvider
): ViewModel() {
    var gameState  by mutableStateOf(GameState())
        private set
    var configState by mutableStateOf(ConfigState())
    private var timerJob: Job? = null
    private var startTime: Long? = null
    private var accumulatedTime: Long? = null

    fun onEvent(event: GameEvent) {

    }
    private fun restartGame() {

    }
    private fun hasTimePassed(): Boolean {
       return false
    }
    private fun startGame() {

    }
    private fun updateTyping(text: String) {

    }
    private fun resumeGame() {

    }
    private fun pauseGame() {

    }
    private fun saveResult() {

    }
    private fun selectGameMode(mode: GameMode) {

    }
    private fun finishGame(clock: Long) {

    }
    private fun startTicker() {

    }
    private fun generateText(): String {
       return ""
    }
}

