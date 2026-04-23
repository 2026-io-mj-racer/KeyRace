package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowTextSeparator(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .width(2.dp)
            .fillMaxHeight()
            .background(Color.Black)
    )
}