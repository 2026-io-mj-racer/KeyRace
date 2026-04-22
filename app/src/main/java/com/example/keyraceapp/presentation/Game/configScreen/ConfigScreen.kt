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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.domain.models.WordCount
import com.example.keyraceapp.presentation.Game.ConfigState

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

            val optionsWithHandlers = mapOf(
                "TRAINING" to {onGameConfigSelected(GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS))},
                "ARCADE" to {onGameConfigSelected(GameMode.Arcade(Difficulty.EASY))}
            )

            GameModeSelectorRow(
                selected = if (configState.gameMode is GameMode.Training) "TRAINING" else "ARCADE",
                optionsWithHandlers = optionsWithHandlers,
            )

            when(val mode = configState.gameMode) {
                is GameMode.Training -> {
                    val optionsWithHandlers = mapOf(
                        "TIME" to {onGameConfigSelected(GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS))},
                        "WORDS" to {onGameConfigSelected(GameMode.Training.WordBased(WordCount.TEN_WORDS))}
                    )

                    GameModeSelectorRow(
                        selected = if(mode is GameMode.Training.TimeBased) "TIME" else "WORDS",
                        optionsWithHandlers = optionsWithHandlers
                    )

                        when(mode) {
                            is GameMode.Training.TimeBased -> {
                                val optionsWithHandlers = mutableMapOf<String, () -> Unit>()

                                for(period in enumValues<TimePeriod>()) {
                                    optionsWithHandlers[period.toString()] = {
                                        onGameConfigSelected(GameMode.Training.TimeBased(period))
                                    }
                                }

                                GameModeSelectorColumn(
                                    selected = mode.time.toString(),
                                    optionsWithHandlers = optionsWithHandlers
                                )
                            }
                            is GameMode.Training.WordBased -> {
                                val optionsWithHandlers = mutableMapOf<String, () -> Unit>()

                                for(count in enumValues<WordCount>()) {
                                    optionsWithHandlers[count.toString()] = {
                                        onGameConfigSelected(GameMode.Training.WordBased(count))
                                    }
                                }

                                GameModeSelectorColumn(
                                    selected = mode.wordCount.toString(),
                                    optionsWithHandlers = optionsWithHandlers
                                )
                           }
                    }
                } is GameMode.Arcade -> {
                    val optionsWithHandlers = mutableMapOf<String, () -> Unit>()

                    for(difficulty in enumValues<Difficulty>()) {
                        optionsWithHandlers[difficulty.toString()] = {
                            onGameConfigSelected(GameMode.Arcade(difficulty))
                        }
                    }

                    GameModeSelectorColumn(
                        selected = mode.difficulty.toString(),
                        optionsWithHandlers = optionsWithHandlers,
                    )
                }
            }

            Button(
                onClick = onNavigateToGameScreen,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
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