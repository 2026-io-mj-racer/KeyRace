package com.example.keyraceapp.presentation.UserProfile

sealed class ProfileEvent {
    object OnResetUserData: ProfileEvent()
    object OnFetchTraining: ProfileEvent()
    object OnFetchArcade: ProfileEvent()

}