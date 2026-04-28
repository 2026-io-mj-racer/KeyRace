package com.example.keyraceapp.presentation.Game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import com.example.keyraceapp.util.TimeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val scoreRepository: ScoreRepository,
    private  val timeProvider: TimeProvider
): ViewModel() {
    var gameState  by mutableStateOf(GameState())
        private set
    var configState by mutableStateOf(ConfigState())
    private var timerJob: Job? = null
    private var startTime: Long? = null
    private var accumulatedTime: Long? = null

    private var mistakesPos = ArrayList<Int>()

    fun onEvent(event: GameEvent) {
        when(event) {
            is GameEvent.OnSelectedGameMode -> selectGameMode(event.gameMode)
            is GameEvent.OnStartGame -> {
                viewModelScope.launch {
                    generateText()
                    //think what needs to go here probably startGame()
                }
            }
            is GameEvent.OnChangeText -> {
                updateTyping(event.text)
            }
            else -> {}
        }
    }
    private fun restartGame() {

    }
    private fun hasTimePassed(): Boolean {
       return false
    }
    private fun startGame() {

    }
    private fun updateTyping(text: String) {

        //TODO: How to update mistakesMAde and correct Words when user backspaces?
        //TODO: SOLVE: When to update mistakes and words after typing the five or during

        val boxLen = gameState.allWords?.get(gameState.currentWordBox)?.length ?: 0

        if(text.length == boxLen - 1) {

            var lastWordCorrect = 0
            val targetText = gameState.allWords!![gameState.currentWordBox]

            if(text[text.length - 1] == targetText[text.length - 1]) {
                lastWordCorrect = 1
            }

            gameState = gameState.copy(
                mistakesMade = (gameState.mistakesMade ?: 0) + lastWordCorrect - 1,
                correctWords = (gameState.correctWords ?: 0) + lastWordCorrect,
                currentWordBox = gameState.currentWordBox + 1,
                typedText = "",
            )

        } else if(text.isNotEmpty()) {
            gameState = gameState.copy(
                typedText = text
            )

            val targetText = gameState.allWords!![gameState.currentWordBox]
            var mistakesMade = 0
            var correctWords = 0
            val typedText = gameState.typedText
            val length = typedText.length

            if(targetText[length - 1] != typedText[length - 1]) {
                mistakesMade++
            }
            if(typedText[length - 1] == ' ' && targetText[length - 1] == ' ') {
                correctWords++
            }

            gameState = gameState.copy(
                mistakesMade = mistakesMade + (gameState.mistakesMade ?: 0),
                correctWords = correctWords + (gameState.correctWords ?: 0)
            )
        } else {
            gameState = gameState.copy(
                typedText = text
            )
        }
    }

    private fun resumeGame() {

    }
    private fun pauseGame() {

    }
    private suspend fun saveResult() {
        val score: Score = Score.buildScore(gameState, configState)

        when(val savingResult = scoreRepository.saveGame(score)) {
            is Resource.Success -> {}
            is Resource.Loading, is Resource.Error -> stateUpdaterLoadingOrError(savingResult)
        }
    }
    private fun selectGameMode(mode: GameMode) {
        configState = configState.copy(
            gameMode = mode
        )
    }
    private fun finishGame(clock: Long) {

    }
    private fun startTicker() {

    }
    private suspend fun generateText() {

        wordRepository.getWords()
            .catch{e -> gameState = gameState.copy(errorMessage = e.message)}
            .collect { response ->
            when (response) {
                is Resource.Success -> {

                    val shuffledWordList = response.data!!.shuffled()
                    val allWordsList = mutableListOf<String>()
                    var i = 0
                    while(i < shuffledWordList.size) {

                        val fiveWords: StringBuilder = StringBuilder("")
                        for(j in 0..4) {
                            fiveWords
                                .append(shuffledWordList[j + i])
                                .append(" ")
                        }

                        allWordsList.add(fiveWords.toString())
                        i += 5;
                    }

                    gameState = gameState.copy(
                        currentWordBox = 0,
                        allWords = allWordsList
                    )
                }
                is Resource.Error, is Resource.Loading -> stateUpdaterLoadingOrError(response)
            }
        }
    }
    private fun<T> stateUpdaterLoadingOrError(value: Resource<T>) {
        gameState = when(value) {
            is Resource.Error -> gameState.copy(errorMessage = value.message)
            is Resource.Loading -> gameState.copy(isLoading = true)
            else -> throw IllegalArgumentException("Wrong value type passed to the method! Please pass Resource.Loading or Resource.Error")
        }
    }
}

