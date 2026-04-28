package com.example.keyraceapp.presentation.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.ui.theme.DeepWhite

@Composable
fun PauseResumeButtons(
    modifier: Modifier = Modifier,
    gameStatus: GameStatus?,
    onResumeGame: () -> Unit,
    onRestartGame: () -> Unit,
    onPauseGame: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(16.dp)
    ) {
        IconButton(onClick = onRestartGame) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Play again button",
                tint = DeepWhite

            )
        }
        when(gameStatus) {
            GameStatus.PLAYING -> {
                IconButton(onClick = onPauseGame) {
                    Icon(
                        imageVector = Icons.Filled.Pause,
                        contentDescription = "Pause button",
                        tint = DeepWhite
                    )
                }
            }

            GameStatus.PAUSED -> {
                IconButton(onClick = onResumeGame) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Resume button",
                        tint = DeepWhite
                    )
                }
            }
            else -> {}
        }
    }
}