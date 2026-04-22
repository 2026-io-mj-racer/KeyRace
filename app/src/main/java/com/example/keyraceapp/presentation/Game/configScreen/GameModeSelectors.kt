package com.example.keyraceapp.presentation.Game.configScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.keyraceapp.ui.theme.BackgroundBlack
import com.example.keyraceapp.ui.theme.DeepWhite

@Composable
fun GameModeSelectorRow(
    optionsWithHandlers: Map<String, () -> Unit>,
    selected: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.padding( 8.dp)){
        for(entry in optionsWithHandlers.entries) {

            val option = entry.key
            val handler = entry.value

            if(option == selected ) {
                Button(
                    onClick = handler,
                    colors = ButtonDefaults.buttonColors(containerColor = DeepWhite),
                    modifier = Modifier.padding(horizontal = 8.dp).weight(1f)
                ) {
                    Text(option, color = Color.Black)
                }

            } else {
                OutlinedButton(
                    onClick = handler,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(option, color = DeepWhite)
                }
            }
        }
    }
}


@Composable
fun GameModeSelectorColumn(
    optionsWithHandlers: Map<String, () -> Unit>,
    selected: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for(entry in optionsWithHandlers.entries) {

            val option = entry.key
            val handler = entry.value

            if(option == selected ) {
                Button(
                    onClick = handler,
                    colors = ButtonDefaults.buttonColors(containerColor = DeepWhite),
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    Text(option, color = Color.Black)
                }

            } else {
                OutlinedButton(
                    onClick = handler,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(option, color = DeepWhite)
                }
            }
        }
    }
}