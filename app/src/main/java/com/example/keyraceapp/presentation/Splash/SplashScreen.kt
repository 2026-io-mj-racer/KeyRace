package com.example.keyraceapp.presentation.Splash

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashTextAnimation(
    text: String,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {

    var state1 by remember{ mutableStateOf("") }

    LaunchedEffect(Unit) {

        state1 = "|"
        for(i in 0..<text.length) {

            state1 = text.take(i)

            delay(100)
        }
        state1 = text
        delay(400)
        onFinished()
    }

    SplashScreenText(text = state1, modifier = modifier)
}
@Composable
fun SplashScreen(
    text: String,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .safeContentPadding(),
    ) {
        SplashTextAnimation(text = text, onFinished = onFinished)

        SplashScreenText(text = "|")
    }
}

@Composable
fun SplashScreenText(text: String, modifier: Modifier = Modifier) {

    Text(
        text = text,
        fontSize = 60.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        modifier = modifier
            .safeContentPadding()
            .animateContentSize()

    )
}