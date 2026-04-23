package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StatsWithLabel(modifier: Modifier = Modifier, label: String, data: String, color: Color = Color.Black) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier

    ) {
        Text(label, color = color, textAlign = TextAlign.Center , style = MaterialTheme.typography.bodyLarge)
        Text(data, color = color,style = MaterialTheme.typography.bodyLarge)


    }
}