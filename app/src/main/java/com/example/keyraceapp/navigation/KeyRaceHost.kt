package com.example.keyraceapp.navigation

import android.util.Log.w
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.presentation.Game.Arcade.ArcadeEvent
import com.example.keyraceapp.presentation.Game.Arcade.ArcadeScreen
import com.example.keyraceapp.presentation.Game.Arcade.ArcadeViewModel
import com.example.keyraceapp.presentation.Game.Training.GameEvent
import com.example.keyraceapp.presentation.Game.Training.GameScreen
import com.example.keyraceapp.presentation.Game.Training.GameViewModel
import com.example.keyraceapp.presentation.Game.configScreen.ConfigScreen
import com.example.keyraceapp.presentation.UserProfile.ProfileEvent
import com.example.keyraceapp.presentation.UserProfile.ProfileViewModel
import com.example.keyraceapp.presentation.UserProfile.profileScreen.ProfileScreen

@Composable
fun KeyRaceHost(
    gameViewModel: GameViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    arcadeViewModel: ArcadeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val arcadeState by arcadeViewModel.state.collectAsStateWithLifecycle()


    NavHost(navController = navController, startDestination = Config) {

        composable<Config> {
            ConfigScreen(
                onNavigateToProfile = {
                    profileViewModel.onEvent(ProfileEvent.OnFetchUser)
                    profileViewModel.onEvent(ProfileEvent.OnFetchTraining)
                    navController.navigate(route = Profile)
                },
                onNavigateToGameScreen = {
                    gameViewModel.onEvent(GameEvent.OnStartGame)
                    navController.navigate(route = Game)

                },
                configState = gameViewModel.configState,
                onGameConfigSelected =  { mode ->
                    gameViewModel.onEvent(GameEvent.OnSelectedGameMode(mode))
                },
                onNavigateToArcadeScreen = {
                    arcadeViewModel.onEvent(ArcadeEvent.OnFetchWords)
                    navController.navigate(route = Arcade)
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
                onNavigateBack = { navController.popBackStack() },
                onResetData = {profileViewModel.onEvent(ProfileEvent.OnResetUserData)},
                onChangeName = {newName -> profileViewModel.onEvent(ProfileEvent.OnChangeName(newName))},
                onShowDialog = {profileViewModel.onEvent(ProfileEvent.OnEditNameClick)},
                onDismissDialog = {profileViewModel.onEvent(ProfileEvent.OnEditNameDismiss)},
                onEditInput = {newName -> profileViewModel.onEvent(ProfileEvent.OnChangeInputName(newName))}
            )
        }

        composable<Game> {
            GameScreen(
                onNavigateBack = { navController.popBackStack() },
                onUpdateTyping = { value ->
                    gameViewModel.onEvent(
                        GameEvent.OnChangeText(value)
                    )
                },
                onPauseGame = {gameViewModel.onEvent(GameEvent.OnPauseGame)},
                onResumeGame = {gameViewModel.onEvent(GameEvent.OnResumeGame)},
                onRestartGame = {gameViewModel.onEvent(GameEvent.OnRestartGame)},
                onPlayAgain = {gameViewModel.onEvent(GameEvent.OnPlayAgain)},
                gameState = gameViewModel.gameState,
            )
        }

        composable<Arcade> {
            ArcadeScreen(
                onUserInput = { input -> arcadeViewModel.onEvent(ArcadeEvent.OnUserInput(input)) },
                state = arcadeState,
                onReachBottom = { word -> arcadeViewModel.onEvent(ArcadeEvent.OnDeleteWord(word)) },
                onNavigateBack = { navController.popBackStack() },
                onStartGame = {arcadeViewModel.onEvent(ArcadeEvent.OnStartGame)}
            )
        }
    }
}