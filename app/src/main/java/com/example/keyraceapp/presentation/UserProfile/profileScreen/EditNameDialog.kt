package com.example.keyraceapp.presentation.UserProfile.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.keyraceapp.ui.theme.DeepWhite
import com.example.keyraceapp.ui.theme.ErrorRed

@Composable
fun EditNameDialog(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit,
    onEditInput: (String) -> Unit,
    editNameValue: String,
    onChangeName: (String) -> Unit


) {
    AlertDialog(
        modifier = modifier,
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
                    value = editNameValue,
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
                TextButton(onClick = {onChangeName(editNameValue)}) {
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