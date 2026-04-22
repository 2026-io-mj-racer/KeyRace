package com.example.keyraceapp.presentation.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.keyraceapp.presentation.components.TopBarWithBackButton

@Composable
fun GameScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopBarWithBackButton(onNavigateBack) },
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()

        ) {
            Text("GAME SCREEN", style = MaterialTheme.typography.bodyLarge)
        }
    }
}