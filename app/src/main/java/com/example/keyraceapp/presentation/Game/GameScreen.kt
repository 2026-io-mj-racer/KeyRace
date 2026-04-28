package com.example.keyraceapp.presentation.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.keyraceapp.presentation.components.TopBarWithBackButton
import com.example.keyraceapp.ui.theme.DeepWhite
import com.example.keyraceapp.ui.theme.ErrorRed

@Composable
fun GameScreen(
    onNavigateBack: () -> Unit,
    onUpdateTyping: (String) -> Unit,
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




            Box(modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
            ){
                val textToType = gameState.allWords?.get(gameState.currentWordBox) ?: "TEXT GENERATOR ERROR"
                val words = textToType.split(" ")
                val firstTwo = words.take(3).joinToString(" ") + " "
                val lastThree = words.drop(3).take(2).joinToString(" ") + " "
                val typedText = gameState.typedText

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    //TODO: Add dyniamic message not this static one change it To Start typing
                    //TODO: display Pause and Playagain buttons
                    //TODO: Change TESTS because a lot of logic has changed
                    Text("PLEASE TAP ANYWHERE TO SHOW KEYBOARD", style = MaterialTheme.typography.bodyLarge)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        for((index, value) in firstTwo.withIndex()) {

                            val isRed =  typedText.length > index && typedText[index] != textToType[index]
                            val isGray = index >= typedText.length

                            val letterToDisplay =
                                if(typedText.length > index) {
                                    typedText[index]
                                } else {
                                    value
                                }

                            Text(
                                text = letterToDisplay.toString(),
                                color = if(isGray) Color.Gray else if(isRed) ErrorRed else DeepWhite,
                                fontSize = 24.sp,
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for((index, value) in lastThree.withIndex()) {
                            val relativeIndex = index + firstTwo.length

                            val isRed =  typedText.length > relativeIndex && typedText[relativeIndex] != textToType[relativeIndex]
                            val isGray = relativeIndex >= typedText.length

                            val letterToDisplay =
                                if(typedText.length > relativeIndex) {
                                    typedText[relativeIndex]
                                } else {
                                    value
                                }

                            Text(
                                text = letterToDisplay.toString(),
                                color = if(isGray) Color.Gray else if(isRed) ErrorRed else DeepWhite,
                                fontSize = 24.sp,
                            )
                        }
                    }
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
    }
}