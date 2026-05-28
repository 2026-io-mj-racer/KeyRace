package com.example.keyraceapp.presentation.Game.Arcade

sealed class ArcadeEvent() {
    object OnStartGame: ArcadeEvent() {}
    data class OnUserInput(val input: String): ArcadeEvent() {}
    data class OnDeleteWord(val word: String): ArcadeEvent() {}
}