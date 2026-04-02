package com.example.keyraceapp.presentation.UserProfile

import androidx.lifecycle.ViewModel
import com.example.keyraceapp.domain.repositories.UserRepository

class ProfileViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    fun onEvent(event: ProfileEvent) {

    }
}

