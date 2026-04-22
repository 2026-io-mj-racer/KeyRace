package com.example.keyraceapp.presentation.Game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.TimeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
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
        when(event) {
            is GameEvent.OnSelectedGameMode -> selectGameMode(event.gameMode)
            else -> {}
        }
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
        configState = configState.copy(
            gameMode = mode
        )
    }
    private fun finishGame(clock: Long) {

    }
    private fun startTicker() {

    }
    private fun generateText(): String {
       return ""
    }
}

