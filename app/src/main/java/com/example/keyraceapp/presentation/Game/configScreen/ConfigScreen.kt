package com.example.keyraceapp.presentation.Game.configScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.domain.models.WordCount
import com.example.keyraceapp.presentation.Game.ConfigState
import com.example.keyraceapp.ui.theme.DeepWhite

@Composable
fun ConfigScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToGameScreen: () -> Unit,
    modifier: Modifier = Modifier,
    configState: ConfigState,
    onGameConfigSelected: (GameMode) -> Unit,
) {

    Scaffold(
        topBar = { ConfigTopBar(onClickProfileIcon = onNavigateToProfile) },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val optionsWithHandlers = remember(onGameConfigSelected) {
                mapOf(
                    "TRAINING" to {onGameConfigSelected(GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS))},
                    "ARCADE" to {onGameConfigSelected(GameMode.Arcade(Difficulty.EASY))}
                )
            }

            GameModeSelectorRow(
                selected = if (configState.gameMode is GameMode.Training) "TRAINING" else "ARCADE",
                optionsWithHandlers = optionsWithHandlers,
            )

            when(val mode = configState.gameMode) {
                is GameMode.Training -> {
                    TrainingConfigSection(
                        onGameConfigSelected = onGameConfigSelected,
                        mode = mode,
                    )

                } is GameMode.Arcade -> {
                    ArcadeConfigSection(
                        mode = mode,
                        onGameConfigSelected = onGameConfigSelected
                    )
                }
            }

            Button(
                onClick = onNavigateToGameScreen,
                colors = ButtonDefaults.buttonColors(containerColor = DeepWhite),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "START",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}