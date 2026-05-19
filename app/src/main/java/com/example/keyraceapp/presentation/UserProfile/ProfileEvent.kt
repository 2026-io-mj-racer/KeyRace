package com.example.keyraceapp.presentation.UserProfile

sealed class ProfileEvent {
    object OnResetUserData: ProfileEvent()
    object OnFetchTraining: ProfileEvent()
    object OnFetchArcade: ProfileEvent()
    object OnFetchUser: ProfileEvent()
    data class OnChangeName(val name: String): ProfileEvent()
    data class OnChangeInputName(val name: String): ProfileEvent()
    object OnEditNameClick: ProfileEvent()
    object OnEditNameDismiss: ProfileEvent()

}