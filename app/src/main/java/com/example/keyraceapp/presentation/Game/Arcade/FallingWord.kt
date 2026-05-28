package com.example.keyraceapp.presentation.Game.Arcade

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.ui.theme.DeepWhite
import com.example.keyraceapp.ui.theme.ErrorRed

@Composable
fun FallingWord(arcadeWord: ArcadeWord, state: ArcadeState, onReachBottom: (String) -> Unit) {
    val offsetY = remember(arcadeWord.word, state.gameStatus) {
        Animatable(if(state.gameStatus == GameStatus.PLAYING) 0f else 150f)
    }

    LaunchedEffect(arcadeWord.word, state.gameStatus) {
        if(state.gameStatus == GameStatus.PLAYING) {
            offsetY.animateTo(
                600f,
                tween(3000, easing = LinearEasing),
            )

            onReachBottom(arcadeWord.word)
        }

    }

    Row (
        //instead of updating offset.Y on each letter
        //just update offset.Y for parent because letters only need to have X
        //this makes it cheaper - because its done in layout phase not in recomposition pahse
        //thanks to .offset { } lambda - so now animation only moves the word - and doesnt require recomposition
        modifier = Modifier.offset { IntOffset(x = 0, y = offsetY.value.dp.roundToPx()) }
    ) {
        for((index, letter) in arcadeWord.word.withIndex()) {

            val correct =
                arcadeWord.word == state.currentTargetWord &&
                        index < state.typedText.length &&
                        state.typedText[index] == letter

            val mistake =
                arcadeWord.word == state.currentTargetWord &&
                        index < state.typedText.length &&
                        state.typedText[index] != letter

            Text(
                text = "$letter",
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                color = if(correct) DeepWhite else if(mistake) ErrorRed else Color.Gray,
                modifier = Modifier
                    .offset(
                        x = (arcadeWord.x + index).dp,
                    )
            )
        }
    }
}