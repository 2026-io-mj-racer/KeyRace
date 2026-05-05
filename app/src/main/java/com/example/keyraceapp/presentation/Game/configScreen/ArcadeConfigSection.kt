package com.example.keyraceapp.presentation.Game.configScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode

@Composable
fun ArcadeConfigSection(
    mode: GameMode.Arcade,
    onGameConfigSelected : (GameMode) -> Unit
) {
    val arcadeOptionsWithHandlers = remember(onGameConfigSelected) {
        enumValues<Difficulty>().associate { difficulty ->
            difficulty.toString() to { onGameConfigSelected(GameMode.Arcade(difficulty)) }
        }
    }

    GameModeSelectorColumn(
        selected = mode.difficulty.toString(),
        optionsWithHandlers = arcadeOptionsWithHandlers,
    )
}