package com.example.keyraceapp.presentation.Game

import android.R.attr.text
import android.util.Log
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
        //TODO: Add queue to handle all of the events and add rate limiting
        //TODO: why because if user bursts game will crash
        val box = gameState.allWords?.get(gameState.currentWordBox)!!
        val boxLen = box.length

        val userTypedSpaceTwoTimesInARow = !(text.isEmpty() || text[text.length - 1] != ' ' || box[text.length - 1] == ' ')
        val userTypedLetterOnSpace = text.isNotEmpty() && text[text.length - 1] != ' ' && box[text.length - 1] == ' '

        if (!userTypedSpaceTwoTimesInARow) {
            if(text.contains(' ')&& text.length == boxLen - 1) {

                val wordsTyped = text.split(" ")
                val wordsInBox = box.trim().split(" ")
                Log.d("HERE BOX LEN AND TEXT LEN", "$wordsTyped\n$wordsInBox")
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

            } else if(!userTypedLetterOnSpace) {
                gameState = gameState.copy(
                    typedText = text
                )
            }
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

