package com.example.keyraceapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.presentation.Game.ConfigScreen
import com.example.keyraceapp.presentation.Game.GameEvent
import com.example.keyraceapp.presentation.Game.GameViewModel
import com.example.keyraceapp.presentation.UserProfile.ProfileScreen
import com.example.keyraceapp.presentation.UserProfile.ProfileViewModel

@Composable
fun KeyRaceHost(
    gameViewModel: GameViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

) {

    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Config) {

        composable<Config> {
            ConfigScreen(
                onNavigateToProfile = {
                    navController.navigate(route = Profile)
                },
                configState = gameViewModel.configState,
                onGameConfigSelected = {
                    gameViewModel.onEvent(GameEvent.OnSelectedGameMode(gameViewModel.configState.gameMode!!))
                }
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