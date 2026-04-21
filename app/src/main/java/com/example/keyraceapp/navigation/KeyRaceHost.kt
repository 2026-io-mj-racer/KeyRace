package com.example.keyraceapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keyraceapp.presentation.Game.ConfigScreen
import com.example.keyraceapp.presentation.UserProfile.ProfileScreen

@Composable
fun KeyRaceHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Config) {

        composable<Config> {
            ConfigScreen(
                onNavigateToProfile = {
                    navController.navigate(route = Profile)
                },
            )
        }
        composable<Profile> {
            ProfileScreen(
                onNavigateBack = {
                    navController.navigate(route = Config)
                },
            )
        }
    }
}