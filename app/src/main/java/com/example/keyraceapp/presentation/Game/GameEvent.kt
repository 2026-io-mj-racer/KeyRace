package com.example.keyraceapp.presentation.Game

import com.example.keyraceapp.domain.models.GameMode

sealed class GameEvent() {
    object OnStartGame: GameEvent()
    object OnPauseGame: GameEvent()
    object OnResumeGame: GameEvent()
    object OnRestartGame: GameEvent()
    object OnPlayAgain: GameEvent()
    data class OnSelectedGameMode(val gameMode: GameMode): GameEvent()
    data class OnChangeText(val text: String): GameEvent()
}