package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.presentation.UserProfile.ProfileState
import com.example.keyraceapp.presentation.components.TopBarWithBackButton
import com.example.keyraceapp.ui.theme.DeepWhite
import com.example.keyraceapp.ui.theme.ErrorRed


@Composable
fun ProfileScreen(
    state: ProfileState,
    onNavigateBack: () -> Unit,
    onSelectedGameMode: (GameMode) -> Unit,
    onResetData: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopBarWithBackButton(onNavigateBack) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Text(
                "Welcome ${state.user.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )

            TopStats(
                wpm = state.topWpm,
                gamesPlayed = state.gamesPlayed,
                wordsTyped = state.wordsTyped
            )


            ScoreTableWithResetButton(
                modifier.align(Alignment.Start),
                onSelectedGameMode = onSelectedGameMode,
                scores = state.topScores,
                isTraining = state.isTraining,
                onResetData = onResetData
            )





        }


    }
}

