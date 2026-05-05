package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.presentation.Game.configScreen.GameModeSelectorRow
import com.example.keyraceapp.ui.theme.DeepWhite

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
        Box(
            modifier = Modifier.background(
                    color = DeepWhite,
                    shape = RoundedCornerShape(16.dp)
            )
            .height(IntrinsicSize.Min)
            .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("WPM\n", modifier = Modifier.padding(bottom = 8.dp), color = Color.Black, fontWeight = FontWeight.SemiBold)

                    scores.forEach { Text("${it.wpm}", color = Color.Black) }
                }

                RowTextSeparator()

                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text("ACC\n", modifier = Modifier.padding(bottom = 8.dp), color = Color.Black,fontWeight = FontWeight.SemiBold)
                    scores.forEach { Text("${it.acc}", color = Color.Black) }
                }

                RowTextSeparator()

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if(isTraining) {
                        Text("CORRECT\nWORDS", modifier = Modifier.padding(bottom = 8.dp), color = Color.Black, fontWeight = FontWeight.SemiBold)
                    } else {
                        Text("POINTS\n", modifier = Modifier.padding(bottom = 8.dp), color = Color.Black, fontWeight = FontWeight.SemiBold)
                    }

                    scores.forEach { it ->
                        when(it) {
                            is Score.TrainingScore -> Text("${it.correctWords}", color = Color.Black)
                            is Score.ArcadeScore -> Text("${it.points}", color = Color.Black)
                        }
                    }
                }
            }
        }

    } else {
        Text("NO DATA", style = MaterialTheme.typography.bodyLarge)
    }
}