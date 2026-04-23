package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.presentation.Game.configScreen.GameModeSelectorRow

@Composable
fun ScoreTable(
    modifier: Modifier = Modifier,
    onSelectedGameMode: (GameMode) -> Unit,
    scores: List<Score>,
    isTraining: Boolean
) {
    Text(
        "TOP 10",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(start = 8.dp,top = 16.dp)
    )

    val optionsWithHandlers: Map<String, () -> Unit> = remember {
        mapOf(
            "TRAINING" to { onSelectedGameMode(GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS))} ,
            "ARCADE" to { onSelectedGameMode(GameMode.Arcade(Difficulty.EASY))}
        )
    }

    GameModeSelectorRow(
        optionsWithHandlers = optionsWithHandlers,
        selected = if (isTraining) "TRAINING" else "ARCADE"
    )

    if(scores.isNotEmpty()) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("WPM", modifier = Modifier.padding(bottom = 8.dp))
                scores.forEach { Text("${it.wpm}") }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text("ACC", modifier = Modifier.padding(bottom = 8.dp))
                scores.forEach { Text("${it.acc}") }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if(isTraining) {
                    Text("CORRECT\nWORDS", modifier = Modifier.padding(bottom = 8.dp))
                } else {
                    Text("POINTS", modifier = Modifier.padding(bottom = 8.dp))
                }

                scores.forEach { it ->
                    when(it) {
                        is Score.TrainingScore -> Text("${it.correctWords}")
                        is Score.ArcadeScore -> Text("${it.points}")
                    }
                }
            }
        }
    } else {
        Text("NO DATA", style = MaterialTheme.typography.bodyLarge)
    }
}