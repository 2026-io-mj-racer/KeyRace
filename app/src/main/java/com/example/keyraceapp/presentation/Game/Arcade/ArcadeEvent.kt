package com.example.keyraceapp.presentation.Game.Arcade

sealed class ArcadeEvent() {
    object OnStartGame: ArcadeEvent() {}
    object OnFetchWords: ArcadeEvent() {}
    object OnPlayAgain: ArcadeEvent() {}
    object OnAssignDifficulty: ArcadeEvent() {}
    data class OnUserInput(val input: String): ArcadeEvent() {}
    data class OnDeleteWord(val word: String): ArcadeEvent() {}
}