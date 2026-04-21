package com.example.keyraceapp.presentation.Game

import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.TimePeriod

data class ConfigState(
    val gameMode: GameMode? = GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS)
)