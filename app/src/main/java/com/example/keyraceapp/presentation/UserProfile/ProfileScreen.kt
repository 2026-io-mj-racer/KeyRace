package com.example.keyraceapp.presentation.UserProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.keyraceapp.presentation.components.TopBarWithBackButton
import com.example.keyraceapp.ui.theme.KeyRaceAppTheme


@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopBarWithBackButton(onNavigateBack) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Text("PROFILE SCREEN", style = MaterialTheme.typography.bodyLarge)
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    KeyRaceAppTheme {
        ProfileScreen(onNavigateBack = {})
    }
}