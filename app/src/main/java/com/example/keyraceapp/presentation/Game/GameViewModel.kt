package com.example.keyraceapp.presentation.Game

import androidx.lifecycle.ViewModel
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository


class GameViewModel(
    private val wordRepository: WordRepository,
    private val scoreRepository: ScoreRepository
): ViewModel() {

    fun onEvent(event: GameEvent) {

    }
    private fun restartGame() {

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
    private fun finishGame() {

    }
    private fun generateText(): String {
       return ""
    }
}

