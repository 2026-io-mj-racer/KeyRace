package com.example.keyraceapp.presentation.UserProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.UserRepository
import com.example.keyraceapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val scoreRepository: ScoreRepository
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set
    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnFetchArcade -> viewModelScope.launch { fetchArcadeData() }
            is ProfileEvent.OnFetchTraining -> viewModelScope.launch { fetchTrainingData() }
            is ProfileEvent.OnResetUserData -> viewModelScope.launch { resetUserData() }
            is ProfileEvent.OnFetchUser -> viewModelScope.launch { fetchUser() }
        }
    }

    private suspend fun fetchUser() {
        val user = userRepository.getUser()

        when(user) {
            is Resource.Success -> state = state.copy(user = user.data!!)
            is Resource.Error, is Resource.Loading -> stateUpdaterLoadingOrError(user)
        }
    }
    private suspend fun fetchArcadeData() {

        coroutineScope {
            this.launch {
                scoreRepository.getTopTenArcade()
                    .catch {e -> state = state.copy(errorMessage = e.message) }
                    .collect { value ->
                        when(value) {
                            is Resource.Success -> {
                                state = state.copy(
                                    topScores = value.data!!,
                                    topWpm = value.data.firstOrNull()?.wpm ?: 0f,
                                )
                            }

                            is Resource.Error, is Resource.Loading -> stateUpdaterLoadingOrError(value)
                        }
                    }
            }

            val totalWordsPromise = async { scoreRepository.getTotalWords() }
            val totalGamesPromise = async { scoreRepository.getTotalGames() }

            when(val totalGames = totalGamesPromise.await()) {
                is Resource.Success -> state = state.copy(gamesPlayed = totalGames.data!!)
                is Resource.Error, is Resource.Loading ->  stateUpdaterLoadingOrError(totalGames)
            }

            when(val totalWords = totalWordsPromise.await()) {
                is Resource.Success -> state = state.copy(wordsTyped = totalWords.data!!)
                is Resource.Error, is Resource.Loading -> stateUpdaterLoadingOrError(totalWords)
            }

        }
    }
    private suspend fun fetchTrainingData() {
        coroutineScope {
            launch {
                scoreRepository.getTopTenTraining()
                    .catch {e -> state = state.copy(errorMessage = e.message) }
                    .collect { value ->
                        when(value) {
                            is Resource.Success -> {
                                state = state.copy(
                                    topScores = value.data!!,
                                    topWpm = value.data.firstOrNull()?.wpm ?: 0f,
                                )
                            }

                            is Resource.Error, is Resource.Loading -> stateUpdaterLoadingOrError(value)
                        }
                    }
            }

            val wordsTypedDeferred = async { scoreRepository.getTotalWords() }
            val gamesPlayedDeferred = async { scoreRepository.getTotalGames() }

            when(val totalWords = wordsTypedDeferred.await()) {
                is Resource.Success -> state = state.copy(wordsTyped = totalWords.data!!)
                is Resource.Loading, is Resource.Error -> stateUpdaterLoadingOrError(totalWords)
            }

            when(val gamesPlayed = gamesPlayedDeferred.await()) {
                is Resource.Success -> state = state.copy(gamesPlayed = gamesPlayed.data!!)
                is Resource.Loading, is Resource.Error -> stateUpdaterLoadingOrError(gamesPlayed)
            }
        }
    }
    private suspend fun resetUserData() {
        val response = userRepository.resetData(state.user)
        when(response) {
            is Resource.Success -> {
                println("HERERERERERE")
                state = ProfileState()
            }
            is Resource.Loading, is Resource.Error -> stateUpdaterLoadingOrError(response)
        }


    }
    private fun<T> stateUpdaterLoadingOrError(value: Resource<T>) {
        state = when(value) {
            is Resource.Error -> state.copy(errorMessage = value.message)
            is Resource.Loading -> state.copy(isLoading = true)
            else -> throw IllegalArgumentException("Wrong value type passed to the method! Please pass Resource.Loading or Resource.Error")
        }
    }
}