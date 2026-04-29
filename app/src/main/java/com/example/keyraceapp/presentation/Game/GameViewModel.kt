package com.example.keyraceapp.presentation.Game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.GameStatus
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

    fun onEvent(event: GameEvent) {
        when(event) {
            is GameEvent.OnSelectedGameMode -> {
                selectGameMode(event.gameMode)
            }
            is GameEvent.OnStartGame -> {
                viewModelScope.launch {

                    if(gameState != GameState()) {
                        gameState = GameState()
                    }

                    generateText()
                }
            }
            is GameEvent.OnChangeText -> viewModelScope.launch {
                updateTyping(event.text)
            }
            is GameEvent.OnPauseGame -> pauseGame()
            is GameEvent.OnResumeGame -> resumeGame()
            is GameEvent.OnRestartGame, is GameEvent.OnPlayAgain -> viewModelScope.launch {
                restartGame()
            }
        }
    }
    private suspend fun restartGame() {
        gameState = GameState()
        generateText()


    }
    private fun hasTimePassed(): Boolean {
       return false
    }
    private fun checkAndStartGame(text: String) {
        if(text.length == 1 && gameState.status == null) {
            gameState = gameState.copy(status = GameStatus.PLAYING)
        }

    }

    private suspend fun updateTyping(text: String) {
        //TODO: Add queue to handle all of the events and add rate limiting
        //TODO: why because if user bursts game will crash

        //STARTING
        checkAndStartGame(text)

        val box = gameState.allWords?.get(gameState.currentWordBox)!!
        val boxLen = box.length

        val userTypedSpaceTwoTimesInARow = !(text.isEmpty() || text[text.length - 1] != ' ' || box[text.length - 1] == ' ')
        val userTypedLetterOnSpace = text.isNotEmpty() && text[text.length - 1] != ' ' && box[text.length - 1] == ' '

        if (!userTypedSpaceTwoTimesInARow) {
            if(text.contains(' ')&& text.length == boxLen - 1) {

                val wordsTyped = text.split(" ")
                val wordsInBox = box.trim().split(" ")
                var mistakesCnt = 0
                var correctWordsCount = 0

                for((index, word) in wordsTyped.withIndex()) {

                    if(word == wordsInBox[index]) {
                        correctWordsCount++
                    } else {
                        for((idx, letter) in word.withIndex()) {
                            if(letter != wordsInBox[index][idx]) {
                                mistakesCnt++
                            }
                        }
                    }
                }
                gameState = gameState.copy(
                    mistakesMade = (gameState.mistakesMade ?: 0) + mistakesCnt,
                    correctWords = (gameState.correctWords ?: 0) + correctWordsCount,
                    currentWordBox = gameState.currentWordBox + 1,
                    typedText = "",
                )
                //FINISHING

                //so if we are in TIME BASED MODE we can finish when the box number is WORDS / 5
                //if we are in time we need to check the time
                checkAndFinishGame()


            } else if(!userTypedLetterOnSpace) {
                gameState = gameState.copy(
                    typedText = text
                )
            }
        }
    }
    private fun resumeGame() {
        if(gameState.status == GameStatus.PAUSED) {
            gameState = gameState.copy(status = GameStatus.PLAYING)
        }
    }
    private fun pauseGame() {
        if(gameState.status == GameStatus.PLAYING) {
            gameState = gameState.copy(status = GameStatus.PAUSED)
        }
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
    private suspend fun checkAndFinishGame(clock: Long? = null) {
        when(val mode = configState.gameMode) {
            is GameMode.Training.WordBased -> {
                val wordCount = mode.wordCount.value.toLong()

                if(wordCount / 5 == gameState.currentWordBox.toLong()) {
                    gameState = gameState.copy(status = GameStatus.FINISHED)
                    saveResult()
                }
            }
            else -> {}
        }
    }
    private fun startTicker() {
    }
    private suspend fun generateText() {

        if(gameState.allWords != null) {
            gameState  =gameState.copy(allWords = gameState.allWords!!.shuffled())
        } else {
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

    }
    private fun<T> stateUpdaterLoadingOrError(value: Resource<T>) {
        gameState = when(value) {
            is Resource.Error -> gameState.copy(errorMessage = value.message)
            is Resource.Loading -> gameState.copy(isLoading = true)
            else -> throw IllegalArgumentException("Wrong value type passed to the method! Please pass Resource.Loading or Resource.Error")
        }
    }
}

