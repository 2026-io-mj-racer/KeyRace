package com.example.keyraceapp.presentation.UserProfile

import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.User


data class ProfileState(
    val topWpm: Float = 0f,
    val topScores: List<Score> = emptyList(),
    val wordsTyped: Int = 0,
    val gamesPlayed: Int = 0,
    val user: User = User("Unknown"),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
