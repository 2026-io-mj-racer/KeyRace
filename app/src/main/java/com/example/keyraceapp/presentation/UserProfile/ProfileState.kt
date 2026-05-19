package com.example.keyraceapp.presentation.UserProfile

import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.User


data class ProfileState(
    val topWpm: Float = 0f,
    val topScores: List<Score> = emptyList(),
    val isTraining: Boolean = true,
    val wordsTyped: Long = 0,
    val gamesPlayed: Long = 0,
    val user: User = User("Unknown"),
    val showEditNameDialog: Boolean = false,
    val editNameInput: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
