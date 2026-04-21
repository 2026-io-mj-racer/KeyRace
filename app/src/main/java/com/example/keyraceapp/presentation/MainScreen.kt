package com.example.keyraceapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.keyraceapp.ui.theme.KeyRaceAppTheme

@Composable
fun MainScreen() {
    Scaffold() { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .safeContentPadding()
                .fillMaxSize(),
        ) {

            Text(
                text = "Main Screen Here",
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    KeyRaceAppTheme {
        MainScreen()
    }
}