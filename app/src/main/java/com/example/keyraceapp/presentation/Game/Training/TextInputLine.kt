package com.example.keyraceapp.presentation.Game.Training

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keyraceapp.ui.theme.DeepWhite
import com.example.keyraceapp.ui.theme.ErrorRed

@Composable
fun TextInputLine(targetText: String,typedText: String, startIndex: Int, lineTargetText: String) {

    val cursorOffset by animateDpAsState(
        targetValue = ((typedText.length - startIndex) * 16).dp,
        animationSpec = tween(durationMillis = 80),
        label = "cursor"
    )

    Box(contentAlignment = Alignment.CenterStart) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            for((index, value) in lineTargetText.withIndex()) {
                val relativeIndex = startIndex + index

                val isRed =  typedText.length > relativeIndex && typedText[relativeIndex] != targetText[relativeIndex]
                val isGray = relativeIndex >= typedText.length
                val letterToDisplay =
                    if(typedText.length  > relativeIndex && typedText[relativeIndex] == targetText[relativeIndex]) {
                        typedText[relativeIndex].toString()
                    } else {
                        value.toString()
                    }

                Box(
                    modifier = Modifier.width(16.dp),
                ){
                    Text(
                        text = letterToDisplay,
                        color = if(isGray) Color.Gray else if(isRed) ErrorRed else DeepWhite,
                        fontSize = 24.sp,
                    )
                }
            }
        }
        val cursorInThisLine = typedText.length >= startIndex &&
                typedText.length < startIndex + lineTargetText.length

        if(cursorInThisLine) {
            Box(
                modifier = Modifier
                        .offset(x = cursorOffset)
                        .width(2.dp)
                        .height(28.dp)
                        .background(DeepWhite)
            )

        }
    }

}