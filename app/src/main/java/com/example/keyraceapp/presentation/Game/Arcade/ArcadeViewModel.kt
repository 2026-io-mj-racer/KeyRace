package com.example.keyraceapp.presentation.Game.Arcade

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.domain.models.TypingCalculator
import com.example.keyraceapp.domain.repositories.ConfigRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.collections.find
import kotlin.collections.plus
import kotlin.random.Random


data class ArcadeWord (val x: Int,  val word: String)

@HiltViewModel
class ArcadeViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val configRepository: ConfigRepository
): ViewModel() {
    private var _state = MutableStateFlow(ArcadeState())
    val state = _state.asStateFlow()
    var allWords: List<String> = emptyList()
    private var spawningJob: Job? = null
    private var difficulty: Difficulty = Difficulty.EASY

    private fun assignDifficulty() {
        when(val mode = configRepository.config.value.gameMode) {
            is GameMode.Arcade -> {
                difficulty = mode.difficulty
            }
            else -> {

            }
        }
    }
    fun onEvent(event: ArcadeEvent) {
        when(event) {
            is ArcadeEvent.OnUserInput -> viewModelScope.launch {
                onUserTyped(event.input)
            }
            is ArcadeEvent.OnDeleteWord -> {
                deleteWord(event.word)
            }
            is ArcadeEvent.OnStartGame -> {
                startSpawningWordsIfNeeded()
                _state.update { current ->
                    current.copy(
                        gameStatus = GameStatus.PLAYING,
                        lives = when(difficulty) {
                            Difficulty.EASY -> 3
                            Difficulty.MEDIUM -> 2
                            Difficulty.HARD -> 1
                        }
                    )
                }
            }
            is ArcadeEvent.OnFetchWords -> {
                viewModelScope.launch {
                    fetchWords()
                }
            }
            is ArcadeEvent.OnPlayAgain -> {
                _state.value = ArcadeState()
                if(spawningJob?.isActive == true) {
                    spawningJob?.cancel()
                }
            }
            is ArcadeEvent.OnAssignDifficulty -> {
                assignDifficulty()
                Log.d("DIFICULTY !!!!!!!!!", "$difficulty")
            }
        }
    }
    private fun deleteWord(word: String) {
        _state.update { current ->
            val updatedWords = current.fallingWords.filter { it.word != word }
            val deletingTarget = word == current.currentTargetWord

            if(current.lives - 1 == 0) {
                current.copy(
                    lives = 0,
                    gameStatus = GameStatus.FINISHED
                )
            } else {
                current.copy(
                    currentTargetWord = if(deletingTarget) "" else current.currentTargetWord,
                    typedText = if(deletingTarget) "" else current.typedText,
                    fallingWords = updatedWords,
                    lives = current.lives - 1
                )
            }
        }
    }

    private fun startSpawningWordsIfNeeded() {

        if(spawningJob?.isActive == true) return

        spawningJob = viewModelScope.launch {
            spawnWords()
        }
    }
    private suspend fun spawnWords() {

        while(true) {
            delay(2000L)

            _state.update { current ->
                val newWord = wordGenerator(current.fallingWords)

                current.copy(fallingWords = current.fallingWords + newWord)
            }
        }
    }
    private fun onUserTyped(input: String) {


        _state.update { current ->
            if(input.length == 1) {

                val matchedWord = current.fallingWords.find { it.word[0] == input[0] }
                if(matchedWord != null) {
                    current.copy(
                        currentTargetWord = matchedWord.word,
                        typedText = input,
                        wholeTypedText = current.typedText + input.length
                    )
                } else {
                    current
                }
            } else if(input.isNotEmpty()) {
                if(input == current.currentTargetWord) {
                    val updatedWords = current.fallingWords.filter{it.word != input}

                    val newPoints = TypingCalculator.computePoints(
                        len = input.length,
                        difficulty = current.difficulty,
                    )
                    current.copy(
                        points = current.points + newPoints,
                        fallingWords = updatedWords,
                        typedText = "",
                        currentTargetWord = "",
                        wholeTypedText = current.typedText + input.length
                    )
                } else {
                    current.copy(
                        wholeTypedText = current.typedText + input.length,
                        typedText = input
                    )
                }
            }
            else {
                current
            }
        }
    }
    private suspend fun fetchWords() {
        wordRepository
            .getWords()
            .catch { throwable ->  _state.update { curr -> curr.copy(errorMsg = throwable.message)}}
            .collect { response ->
                when(response) {
                    is Resource.Success -> {
                        allWords = response.data!!
                    }
                    is Resource.Loading -> {
                        _state.update { curr -> curr.copy(isLoading = true) }
                    }
                    is Resource.Error -> {
                        _state.update { curr -> curr.copy(errorMsg = response.message)}
                    }
                }
            }
    }
    private fun wordGenerator(wordsOnScreen: List<ArcadeWord>): ArcadeWord {
        //Lets assume that words wont ever overlap
        //this means at most one word can be generated in one iteration(animation pahse)
        //Generator makes sure that only one word with the same starting letter is on the screen
        val rd = Random
        val words = allWords

        val firstLettersList = mutableListOf<Char>()
        wordsOnScreen.forEach { arcadeWord ->
            firstLettersList.add(arcadeWord.word[0])
        }

        var sameFirstLetter = true
        var nextWord = ArcadeWord(-1, "" )
        while(sameFirstLetter) {

            nextWord = ArcadeWord(x = rd.nextInt(300), word = words[rd.nextInt(words.size)])
            if(!firstLettersList.contains(nextWord.word[0])) {
                sameFirstLetter = false
            }
        }

        return nextWord
    }
}

