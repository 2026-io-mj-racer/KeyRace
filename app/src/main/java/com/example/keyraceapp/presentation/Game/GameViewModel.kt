package com.example.keyraceapp.presentation.Game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.TypingCalculator
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import com.example.keyraceapp.util.TimeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
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
                gameLoop(event.text)

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
    private suspend fun timer() {
        val mode = configState.gameMode
        if(mode is GameMode.Training.TimeBased) {

            val targetTime = mode.time.value.toLong()

            if(gameState.status == GameStatus.PLAYING) {

                    while((gameState.elapsedTime!! / 1000L) < targetTime) {
                        delay(100L)
                        gameState = gameState.copy(elapsedTime = timeProvider.now() - startTime!!)
                    }
            }
        }
    }

    private suspend fun gameLoop(text: String) {
        while(gameState.status != GameStatus.FINISHED) {
            coroutineScope {
                launch { updateTyping(text) }

                launch { timer() }
            }
        }
    }
    private fun checkAndStartGame(text: String) {
        if(text.length == 1 && gameState.status == null) {

            startTime = timeProvider.now()
            gameState = gameState.copy(
                status = GameStatus.PLAYING,
                elapsedTime = 0L,
                lenOverall = 0L,
                mistakesMade = 0,
                correctWords = 0
            )
        }
    }
    private fun updateTyping(text: String) {
        //TODO: Add queue to handle all of the events and add rate limiting
        //TODO: why because if user bursts game will crash

        checkAndStartGame(text)

        if(gameState.status == GameStatus.PLAYING) {


            val box = gameState.allWords?.get(gameState.currentWordBox)!!
            val boxLen = box.length

            val userTypedSpaceTwoTimesInARow = !(text.isEmpty() || text[text.length - 1] != ' ' || box[text.length - 1] == ' ')
            val userTypedLetterOnSpace = text.isNotEmpty() && text[text.length - 1] != ' ' && box[text.length - 1] == ' '

            if (!userTypedSpaceTwoTimesInARow) {
                val userFinishedBox = text.length == boxLen - 1

                if(userFinishedBox || isFinished()) {

                    val (mistakesCnt, correctWordsCnt) = countMistakesAndCorrectWords(text, box)

                    gameState = gameState.copy(
                        mistakesMade = gameState.mistakesMade!!  + mistakesCnt,
                        correctWords = gameState.correctWords!!  + correctWordsCnt,
                        currentWordBox = gameState.currentWordBox + 1,
                        lenOverall = gameState.lenOverall!!  + text.length,
                        elapsedTime = timeProvider.now() - startTime!!,
                        typedText = "",
                    )
                    computeAccAndWpm()

                    if(isFinished()) {
                        finishAndSave()
                    }

                } else if(!userTypedLetterOnSpace) {
                    gameState = gameState.copy(
                        typedText = text
                    )
                }
            }
        }
    }
    private fun countMistakesAndCorrectWords(text: String, box: String): Pair<Int, Int> {

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

        return Pair(mistakesCnt, correctWordsCount)
    }

    private fun computeAccAndWpm() {
        viewModelScope.launch {

            val acc = TypingCalculator.computeAcc(
                length = gameState.lenOverall!!.toInt(),
                mistakesMade = gameState.mistakesMade!!
            )
            val wpm = TypingCalculator.computeWpm(
                elapsedTime = gameState.elapsedTime!!.toFloat(),
                length = gameState.lenOverall!!.toInt()
            )

            val prevAvgWpm = gameState.currentWpm ?: 0f
            var avgWpm = ((gameState.currentWordBox - 1) * prevAvgWpm + wpm ) / (gameState.currentWordBox)

            avgWpm = BigDecimal(avgWpm.toDouble())
                                    .setScale(2, RoundingMode.HALF_EVEN)
                                    .toFloat()


            gameState = gameState.copy(
                currentWpm = avgWpm,
                currentAcc = acc
            )
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
    private fun isFinished(): Boolean {
        return when(val mode = configState.gameMode) {
            is GameMode.Training.WordBased -> {
                val wordCount = mode.wordCount.value.toLong()
                wordCount / 5 == gameState.currentWordBox.toLong()
            }
            is GameMode.Training.TimeBased -> {
                val targetTime = mode.time.value.toLong()
                targetTime <= gameState.elapsedTime!! / 1000L
            }
            else -> {
                false
            }
        }
    }

    private fun finishAndSave() {
        gameState = gameState.copy(status = GameStatus.FINISHED)
        viewModelScope.launch(Dispatchers.IO) {
            saveResult()
        }
    }
    private suspend fun generateText() {

        if(gameState.allWords != null) {
            gameState = gameState.copy(allWords = gameState.allWords!!.shuffled())
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

