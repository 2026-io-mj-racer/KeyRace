package com.example.keyraceapp.presentation.Game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.ui.theme.KeyRaceAppTheme

@Composable
fun ConfigScreen(
    onNavigateToProfile: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                onClickProfileIcon = onNavigateToProfile
            )
         } ,
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .safeContentPadding()
                .fillMaxSize(),
        ) {

            Text(
                text = "Config Screen Here",
                style = MaterialTheme.typography.bodyLarge
            )


        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onClickProfileIcon: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = onClickProfileIcon,
                modifier = modifier.safeContentPadding()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                )
            }
        },
        modifier = modifier.safeContentPadding()
    )
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    KeyRaceAppTheme {
        ConfigScreen({println("Navigating")})
    }
}