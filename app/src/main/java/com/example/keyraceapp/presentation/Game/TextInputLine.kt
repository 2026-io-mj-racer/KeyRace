package com.example.keyraceapp.presentation.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.keyraceapp.ui.theme.DeepWhite
import com.example.keyraceapp.ui.theme.ErrorRed

@Composable
fun TextInputLine(targetText: String,typedText: String, startIndex: Int, lineTargetText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        for((index, value) in lineTargetText.withIndex()) {
            val relativeIndex = startIndex + index

            val isRed =  typedText.length > relativeIndex && typedText[relativeIndex] != targetText[relativeIndex]
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