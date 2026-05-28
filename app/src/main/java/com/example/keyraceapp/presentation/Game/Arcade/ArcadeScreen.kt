package com.example.keyraceapp.presentation.Game.Arcade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.presentation.components.TopBarWithBackButton
import com.example.keyraceapp.ui.theme.DeepWhite


@Composable
fun ArcadeScreen(
    modifier: Modifier = Modifier,
    state: ArcadeState,
    onUserInput: (String) -> Unit,
    onReachBottom: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onStartGame: () -> Unit,
    onPlayAgain: () -> Unit
) {
    Scaffold(
        topBar = { TopBarWithBackButton(onNavigateBack) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
//TODO: add getting words from input file and generating them so that no two words with the same first letter are at the same time on the screen

        if(state.gameStatus == GameStatus.FINISHED) {
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("GAME OVER", style = MaterialTheme.typography.titleLarge)
                Text("SCORE = ${state.points}", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = onPlayAgain) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Play again button",
                        modifier = Modifier.size(32.dp)

                    )
                }

            }
        } else  {
            Box(modifier = Modifier.fillMaxSize()) {

                if (state.gameStatus == GameStatus.PLAYING) {
                    state.fallingWords.forEach { arcadeWord ->
                        key(arcadeWord.word) {
                            FallingWord(
                                arcadeWord = arcadeWord,
                                state = state,
                                onReachBottom = onReachBottom,
                            )
                        }
                    }
                } else {
                    Text(
                        "TYPE ANY LETTER TO START PLAYING",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.TopCenter).padding(innerPadding),
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .offset(y = 600.dp)
                        .background(DeepWhite)
                ) {}

                BasicTextField(
                    modifier = Modifier.fillMaxSize().alpha(0f),
                    value = state.typedText,
                    onValueChange = { input ->
                        if (state.gameStatus == GameStatus.PLAYING) {
                            onUserInput(input)
                        } else {
                            onStartGame()
                        }
                    }
                )
            }

        }
    }




}
