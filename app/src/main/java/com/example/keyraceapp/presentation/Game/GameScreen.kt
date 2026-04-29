package com.example.keyraceapp.presentation.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.presentation.components.TopBarWithBackButton

@Composable
fun GameScreen(
    onNavigateBack: () -> Unit,
    onUpdateTyping: (String) -> Unit,
    onPlayAgain: () -> Unit,
    onPauseGame: () -> Unit,
    onRestartGame: () -> Unit,
    onResumeGame: () -> Unit,
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopBarWithBackButton(onNavigateBack) },
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(contentPadding)
        ) {
           if(gameState.status == GameStatus.FINISHED)  {
               Text(
                   text = "GAME STATS",
                   style = MaterialTheme.typography.titleLarge,
                   modifier = Modifier.padding(16.dp)
               )

               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(
                       "WPM",
                       style = MaterialTheme.typography.titleLarge,
                       modifier = Modifier.padding(16.dp)
                   )
                   Text(
                       "ACC",
                       style = MaterialTheme.typography.titleLarge,
                       modifier = Modifier.padding(16.dp)
                   )
               }
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(
                       text = "${gameState.currentWpm}",
                       modifier = Modifier.padding(16.dp)
                   )

                   Text(
                       text = "${gameState.currentAcc}",
                       modifier = Modifier.padding(16.dp)
                   )
               }

               IconButton(onClick = onPlayAgain) {
                   Icon(
                       imageVector = Icons.Default.Replay,
                       contentDescription = "Play again button",
                       modifier = Modifier.size(32.dp)

                   )
               }

           } else {
               PauseResumeButtons(
                   onPauseGame = onPauseGame,
                   onRestartGame = onRestartGame,
                   onResumeGame = onResumeGame,
                   gameStatus = gameState.status
               )
               GameInputDisplay(
                   gameState = gameState,
                   onUpdateTyping = onUpdateTyping
               )
           }
        }
    }
}