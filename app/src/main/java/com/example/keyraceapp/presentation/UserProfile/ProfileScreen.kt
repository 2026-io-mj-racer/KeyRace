package com.example.keyraceapp.presentation.UserProfile

import android.R.attr.data
import android.R.attr.label
import android.system.Os.stat
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.presentation.Game.configScreen.GameModeSelectorRow
import com.example.keyraceapp.presentation.components.TopBarWithBackButton
import com.example.keyraceapp.ui.theme.DeepWhite


@Composable
fun ProfileScreen(
    state: ProfileState,
    onNavigateBack: () -> Unit,
    onSelectedGameMode: (GameMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopBarWithBackButton(onNavigateBack) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Text(
                "Welcome ${state.user.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )

            Box(
                modifier = Modifier
                    .background(
                        color = DeepWhite,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {

                    StatsWithLabel(label = "TOP\nWPM", data = "${state.topWpm}")
                    RowTextSeparator()
                    StatsWithLabel(label = "GAMES\nPLAYED", data = "${state.gamesPlayed}")
                    RowTextSeparator()
                    StatsWithLabel(label = "WORDS\nTYPED", data = "${state.wordsTyped}")

                }
            }

            Text(
                "TOP 10",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp,top = 16.dp)

            )

            val optionsWithHandlers: Map<String, () -> Unit> = mapOf(
                "TRAINING" to { onSelectedGameMode(GameMode.Training.TimeBased(TimePeriod.FIFTEEN_SECONDS))} ,
                "ARCADE" to { onSelectedGameMode(GameMode.Arcade(Difficulty.EASY))}
            )

            GameModeSelectorRow(
                optionsWithHandlers = optionsWithHandlers,
                selected = if (state.isTraining) "TRAINING" else "ARCADE"
            )

            if(state.topScores.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("WPM", modifier = Modifier.padding(bottom = 8.dp))
                        state.topScores.forEach { Text("${it.wpm}") }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text("ACC", modifier = Modifier.padding(bottom = 8.dp))
                        state.topScores.forEach { Text("${it.acc}") }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        when(state.isTraining) {
                            true -> Text("CORRECT\nWORDS", modifier = Modifier.padding(bottom = 8.dp))
                            false -> Text("POINTS", modifier = Modifier.padding(bottom = 8.dp))
                        }

                        state.topScores.forEach { it ->
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
    }
}

@Composable
fun RowTextSeparator(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .width(2.dp)
            .fillMaxHeight()
            .background(Color.Black)
    )
}

@Composable
fun StatsWithLabel(modifier: Modifier = Modifier, label: String, data: String, color: Color = Color.Black) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier

        ) {
        Text(label, color = color, textAlign = TextAlign.Center , style = MaterialTheme.typography.bodyLarge)
        Text(data, color = color,style = MaterialTheme.typography.bodyLarge)


    }
}