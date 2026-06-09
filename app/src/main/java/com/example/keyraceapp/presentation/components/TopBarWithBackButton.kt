package com.example.keyraceapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBackButton(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    dataTitleOne: String? = null,
    valueOne: String? = null,
    dataTitleTwo: String? = null,
    valueTwo: String? = null
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back button",
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        actions = {
            if(valueOne != null) {
                Text(
                    "$dataTitleOne: $valueOne",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            if(valueTwo != null) {
                Text(
                    "$dataTitleTwo: $valueTwo",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        },
        modifier = modifier
    )
}