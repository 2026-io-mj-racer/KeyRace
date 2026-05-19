package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onChangeName: (String) -> Unit,
    onDismissDialog: () -> Unit,
    onShowDialog: () -> Unit,
    onEditInput: (String) -> Unit,
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

            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
            ) {
                Text(
                    "Welcome ${state.user.name}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(8.dp)
                )

                IconButton(
                    onClick = onShowDialog
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit icon",
                    )
                }

                if(state.showEditNameDialog) {
                    AlertDialog(
                        onDismissRequest = onDismissDialog,
                        title = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    "EDIT USERNAME",
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }

                        },
                        text = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = state.editNameInput,
                                    onValueChange = { onEditInput(it) },
                                    textStyle = MaterialTheme.typography.bodyLarge,

                                    )
                            }
                        },
                        confirmButton = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(onClick = {onChangeName(state.editNameInput)}) {
                                    Text("SAVE", style = MaterialTheme.typography.titleLarge)
                                }
                                TextButton(onClick = onDismissDialog) {
                                    Text("CANCEL", style = MaterialTheme.typography.titleLarge, color = ErrorRed)
                                }
                            }

                        },
                        containerColor = Color(0xFF1A1A1A),
                        textContentColor = DeepWhite

                    )
                }

            }

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

