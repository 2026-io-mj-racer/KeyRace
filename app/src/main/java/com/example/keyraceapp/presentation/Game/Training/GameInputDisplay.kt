package com.example.keyraceapp.presentation.Game.Training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun GameInputDisplay(gameState: GameState, onUpdateTyping: (String) -> Unit) {
    Box(modifier = Modifier
        .safeContentPadding()
    ){
        val textToType = gameState.allWords?.get(gameState.currentWordBox) ?: "TEXT GENERATOR ERROR"
        val words = textToType.split(" ")
        val firstTwo = words.take(3).joinToString(" ") + " "
        val lastThree = words.drop(3).take(2).joinToString(" ") + " "
        val typedText = gameState.typedText

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            //TODO: Add dyniamic message not this static one change it To Start typing
            Text(
                "PLEASE TAP ANYWHERE TO SHOW KEYBOARD",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )

            TextInputLine(
                targetText = textToType,
                typedText = typedText,
                startIndex = 0,
                lineTargetText = firstTwo
            )

            TextInputLine(
                targetText =  textToType,
                typedText = typedText,
                startIndex = firstTwo.length,
                lineTargetText = lastThree
            )
        }

        BasicTextField(
            value = gameState.typedText,
            onValueChange = { onUpdateTyping(it) },
            modifier = Modifier
                .fillMaxSize()
                .alpha(0f)

        )
    }
}