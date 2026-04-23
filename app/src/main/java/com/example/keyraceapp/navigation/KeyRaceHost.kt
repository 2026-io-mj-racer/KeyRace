package com.example.keyraceapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.presentation.Game.configScreen.ConfigScreen
import com.example.keyraceapp.presentation.Game.GameEvent
import com.example.keyraceapp.presentation.Game.GameScreen
import com.example.keyraceapp.presentation.Game.GameViewModel
import com.example.keyraceapp.presentation.UserProfile.ProfileEvent
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
                    profileViewModel.onEvent(ProfileEvent.OnFetchUser)
                    profileViewModel.onEvent(ProfileEvent.OnFetchTraining)
                    navController.navigate(route = Profile)
                },
                onNavigateToGameScreen = {
                    navController.navigate(route = Game)

                },
                configState = gameViewModel.configState,
                onGameConfigSelected =  { mode ->
                    gameViewModel.onEvent(GameEvent.OnSelectedGameMode(mode))
                }
            )
        }

        composable<Profile> {
            ProfileScreen(
                state = profileViewModel.state,
                onSelectedGameMode = { mode ->
                    when(mode) {
                        is GameMode.Training -> profileViewModel.onEvent(ProfileEvent.OnFetchTraining)
                        is GameMode.Arcade -> profileViewModel.onEvent(ProfileEvent.OnFetchArcade)
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Game> {
            GameScreen(onNavigateBack = {navController.popBackStack()})
        }

    }
}