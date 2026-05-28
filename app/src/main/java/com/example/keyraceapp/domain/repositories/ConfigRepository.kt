package com.example.keyraceapp.domain.repositories

import com.example.keyraceapp.presentation.Game.ConfigState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepository @Inject constructor() {
    var config = MutableStateFlow(ConfigState())
}