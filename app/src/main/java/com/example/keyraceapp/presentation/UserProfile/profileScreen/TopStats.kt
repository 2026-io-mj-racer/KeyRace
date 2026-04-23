package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.ui.theme.DeepWhite

@Composable
fun TopStats(
    modifier: Modifier = Modifier,
    wpm: Float,
    gamesPlayed: Long,
    wordsTyped: Long,
) {
    Box(
        modifier = Modifier
            .background(
                color = DeepWhite,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {

            StatsWithLabel(label = "TOP\nWPM", data = "$wpm")
            RowTextSeparator()
            StatsWithLabel(label = "GAMES\nPLAYED", data = "$gamesPlayed")
            RowTextSeparator()
            StatsWithLabel(label = "WORDS\nTYPED", data = "$wordsTyped")
        }
    }
}