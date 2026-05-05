package com.example.keyraceapp.presentation.Game.configScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.domain.models.WordCount


@Composable
fun TrainingConfigSection(
    mode: GameMode.Training,
    onGameConfigSelected: (GameMode) -> Unit
) {
    val optionsWithHandlers = remember(onGameConfigSelected) {
        mapOf(
            "TIME" to {onGameConfigSelected(GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS))},
            "WORDS" to {onGameConfigSelected(GameMode.Training.WordBased(WordCount.TEN_WORDS))}
        )
    }


    GameModeSelectorRow(
        selected = if(mode is GameMode.Training.TimeBased) "TIME" else "WORDS",
        optionsWithHandlers = optionsWithHandlers
    )

    when(mode) {
        is GameMode.Training.TimeBased -> {

            val timeOptionsWithHandlers = remember(onGameConfigSelected) {
                enumValues<TimePeriod>().associate { period ->
                    period.toString() to { onGameConfigSelected(GameMode.Training.TimeBased(period)) }
                }
            }

            GameModeSelectorColumn(
                selected = mode.time.toString(),
                optionsWithHandlers = timeOptionsWithHandlers
            )
        }
        is GameMode.Training.WordBased -> {

            val wordCountOptionsWithHandlers = remember(onGameConfigSelected) {
                enumValues<WordCount>().associate { wordCount ->
                    wordCount.toString() to  {onGameConfigSelected(GameMode.Training.WordBased(wordCount))}
                }
            }

            GameModeSelectorColumn(
                selected = mode.wordCount.toString(),
                optionsWithHandlers = wordCountOptionsWithHandlers
            )
        }
    }
}