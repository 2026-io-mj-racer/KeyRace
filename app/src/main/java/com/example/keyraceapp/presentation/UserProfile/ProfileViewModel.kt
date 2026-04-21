package com.example.keyraceapp.presentation.UserProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val scoreRepository: ScoreRepository
): ViewModel() {

    var state by mutableStateOf<ProfileState>(ProfileState())
        private set
    fun onEvent(event: ProfileEvent) {

    }

    private fun fetchUser() {

    }
    private fun fetchTrainingData() {

    }
    private fun fetchArcadeData() {

    }
    private fun resetUserData() {

    }
}

