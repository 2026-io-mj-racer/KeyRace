package com.example.keyraceapp

import android.util.Log.v
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.domain.models.WordCount
import com.example.keyraceapp.domain.repositories.ConfigRepository
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.presentation.Game.Arcade.ArcadeEvent
import com.example.keyraceapp.presentation.Game.Arcade.ArcadeViewModel
import com.example.keyraceapp.presentation.Game.ConfigState
import com.example.keyraceapp.presentation.Game.Training.GameEvent
import com.example.keyraceapp.presentation.Game.Training.GameState
import com.example.keyraceapp.presentation.Game.Training.GameViewModel
import com.example.keyraceapp.util.Resource
import com.example.keyraceapp.util.TimeProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Every
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.assertEquals

class FakeTimeProvider: TimeProvider {
    var currentTime = 0L
    override fun now(): Long =  currentTime
    fun advanceBy(time: Long) {
        currentTime += time;
    }
}
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private var scoreRepository = mockk<ScoreRepository>()
    private var wordRepository = mockk<WordRepository>()
    private var configRepository = mockk<ConfigRepository>()
    private val timeProvider = FakeTimeProvider()
    private val testDispatcher = StandardTestDispatcher()
    val exampleTimeMode = GameMode.Training.TimeBased(TimePeriod.THIRTY_SECONDS)
    val exampleWordMode = GameMode.Training.WordBased(WordCount.TEN_WORDS)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { wordRepository.getWords() } returns flowOf(
            Resource.Success(
                listOf(
                    "HA",
                    "HI",
                    "HO",
                    "HU",
                    "HE",
                    "A",
                    "B",
                    "C",
                    "D",
                    "E"
                )
            )
        )
        every { configRepository.config } returns MutableStateFlow(
            ConfigState(
                gameMode = GameMode.Training.TimeBased(
                    TimePeriod.FIFTEEN_SECONDS
                )
            )
        )

        viewModel = GameViewModel(
            scoreRepository = scoreRepository,
            wordRepository = wordRepository,
            configRepository = configRepository,
            timeProvider = timeProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `restartGame() - TIMEBASED - set state into initial values except the target text after restart`() =
        runTest(testDispatcher) {

            val expectedGameState = GameState(
                currentWordBox = 0,
                elapsedTime = null,
                correctWords = null,
                mistakesMade = null,
                currentWpm = null,
                status = null,
                typedText = "",
            )
            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
            viewModel.onEvent(GameEvent.OnStartGame)
            viewModel.onEvent(GameEvent.OnChangeText("H "))
            runCurrent()

            timeProvider.advanceBy(10)
            advanceTimeBy(50)
            runCurrent()
            viewModel.onEvent(GameEvent.OnRestartGame)

            //assert one by one because of converting targetText to sortedSet
            expectThat(viewModel.gameState) {
                get { currentWordBox }.isEqualTo(expectedGameState.currentWordBox)
                get { points }.isEqualTo(expectedGameState.points)
                get { fallingSpeed }.isEqualTo(expectedGameState.fallingSpeed)
                get { status }.isEqualTo(expectedGameState.status)
                get { elapsedTime }.isEqualTo(expectedGameState.elapsedTime)
                get { currentAcc }.isEqualTo(expectedGameState.currentAcc)
                get { mistakesMade }.isEqualTo(expectedGameState.mistakesMade)
                get { lives }.isEqualTo(expectedGameState.lives)
                get { typedText }.isEqualTo(expectedGameState.typedText)
                get { correctWords }.isEqualTo(expectedGameState.correctWords)
                get { currentWpm }.isEqualTo(expectedGameState.currentWpm)
            }
        }

    /*@Test
    //This test doesnt make sens because wwe dont need to return generated text we can just set it into state
    //because of that change I commented it out.
    fun `generateText() - joins list of strings into one string with words separated by spaces `() =  runTest  {

        val expectedText = "HA HI"

        viewModel.onEvent(GameEvent.OnStartGame)

        assertEquals(expectedText.toSortedSet(), viewModel.gameState.targetText!!.toSortedSet(), message = "Check if targetText isn't null!!!!")
    }*/

    @Test
    fun `OnSelectedGameMode - sets configState correctly when invoked`() {

        val mode = GameMode.Training.TimeBased(TimePeriod.THIRTY_SECONDS)
        val expectedConfigState = ConfigState(mode)

        viewModel.onEvent(GameEvent.OnSelectedGameMode(mode))

        assertEquals(expectedConfigState, viewModel.configState.value)

    }

    @Test
    fun `startGame() - TIMEBASED - timer starts after user types the first letter and advances the elapsedTime in state`() =
        runTest(testDispatcher) {

            try {
                viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
                viewModel.onEvent(GameEvent.OnStartGame)
                viewModel.onEvent(GameEvent.OnChangeText("a"))
                //here starTick job maybe scheduled but not ran so we run it manually
                runCurrent()

                //now after starting the clock lets advance our fake timer to simulate the gameplay
                timeProvider.advanceBy(2000)
                //now we also need to advance to Dispatcher clock so our routine is run
                advanceTimeBy(101)
                //again run scheduled coroutines BECAUSE WE NEED TO SEE THE CHANGE IN TIME
                runCurrent()

                assertEquals(2000L, viewModel.gameState.elapsedTime)
            } finally {
                viewModel.stopTimer()
            }
        }

    @Test
    fun `startGame() - TIMEBASED - timer does not start after user clicked Start but didnt type the first letter`() =
        runTest(testDispatcher) {

            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
            viewModel.onEvent(GameEvent.OnStartGame)
            runCurrent() //see if timer starts - it should NOT

            assertEquals(null, viewModel.gameState.elapsedTime)

        }

    @Test
    fun `startGame() - sets each of the state values to GameState based on ConfigState when OnStartGame invoked`() =
        runTest(testDispatcher) {
            val expectedGameState = GameState()
            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))


            viewModel.onEvent(GameEvent.OnStartGame)

            expectThat(viewModel.gameState) {
                get { points }.isEqualTo(expectedGameState.points)
                get { fallingSpeed }.isEqualTo(expectedGameState.fallingSpeed)
                get { status }.isEqualTo(expectedGameState.status)
                get { elapsedTime }.isEqualTo(expectedGameState.elapsedTime)
                get { currentAcc }.isEqualTo(expectedGameState.currentAcc)
                get { mistakesMade }.isEqualTo(expectedGameState.mistakesMade)
                get { lives }.isEqualTo(expectedGameState.lives)
                get { typedText }.isEqualTo(expectedGameState.typedText)
                get { correctWords }.isEqualTo(expectedGameState.correctWords)
                get { currentWpm }.isEqualTo(expectedGameState.currentWpm)
            }
        }

    @Test
    fun `updateTyping() - updates correctly the number of mistakes and words typed when user makes only mistakes`() =
        runTest(testDispatcher) {
            try {
                val expectedMistakes = 0
                val expectedCorrectWords = 5

                viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
                viewModel.onEvent(GameEvent.OnStartGame)
                runCurrent()

                val firstBucket = viewModel.gameState.allWords!![0]
                viewModel.onEvent(GameEvent.OnChangeText(firstBucket[0].toString()))
                runCurrent()

                viewModel.onEvent(GameEvent.OnChangeText(firstBucket.take(firstBucket.length - 1)))
                runCurrent()

                expectThat(viewModel.gameState) {
                    get { mistakesMade }.isEqualTo(expectedMistakes)
                    get { correctWords }.isEqualTo(expectedCorrectWords)
                }
            } finally {
                viewModel.stopTimer()
            }

        }

    @Test
    fun `updateTyping() - updates correctly the number of mistakes and words typed when user type correct words`() =
        runTest(testDispatcher) {
            try {
                val expectedMistakes = 1
                val expectedCorrectWords = 4

                viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
                viewModel.onEvent(GameEvent.OnStartGame)
                runCurrent()

                val userInput = StringBuilder(viewModel.gameState.allWords!![0])
                userInput.deleteCharAt(0)
                userInput.insert(0, "X")

                viewModel.onEvent(GameEvent.OnChangeText(userInput[0].toString()))
                runCurrent()

                viewModel.onEvent(
                    GameEvent.OnChangeText(
                        userInput.take(userInput.length - 1).toString()
                    )
                )
                runCurrent()

                expectThat(viewModel.gameState) {
                    get { mistakesMade }.isEqualTo(expectedMistakes)
                    get { correctWords }.isEqualTo(expectedCorrectWords)
                }
            } finally {
                viewModel.stopTimer()
            }
        }


    @Test
    fun `pauseGame() - set status to PAUSED when OnGamePause invoked`() = runTest(testDispatcher) {
        try {
            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleWordMode))
            viewModel.onEvent(GameEvent.OnStartGame)
            viewModel.onEvent(GameEvent.OnChangeText("x"))
            runCurrent()

            viewModel.onEvent(GameEvent.OnPauseGame)
            runCurrent()


            assertEquals(GameStatus.PAUSED, viewModel.gameState.status)
        } finally {
            viewModel.stopTimer()
        }

    }

    @Test
    fun `pauseGame() - pauses the timer when OnGamePause invoked`() = runTest(testDispatcher) {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        viewModel.onEvent(GameEvent.OnChangeText("x"))
        runCurrent()

        timeProvider.advanceBy(3000L)
        advanceTimeBy(101)
        runCurrent()

        assertEquals(GameStatus.PLAYING, viewModel.gameState.status)
        assertEquals(3000L, viewModel.gameState.elapsedTime)

        viewModel.onEvent(GameEvent.OnPauseGame)
        timeProvider.advanceBy(1000L)
        runCurrent()

        assertEquals(3000L, viewModel.gameState.timeBeforePauses)
        assertEquals(0L, viewModel.gameState.elapsedTime)
    }

    @Test
    fun `resumeGame() sets status to PLAYING when OnResume is invoked`() = runTest(testDispatcher) {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleWordMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        viewModel.onEvent(GameEvent.OnChangeText("x"))
        viewModel.onEvent(GameEvent.OnPauseGame)

        viewModel.onEvent(GameEvent.OnResumeGame)
        runCurrent()

        assertEquals(GameStatus.PLAYING, viewModel.gameState.status)

    }

    @Test
    fun `resumeGame() resumes timer when OnResume is invoked`() = runTest(testDispatcher) {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        viewModel.onEvent(GameEvent.OnChangeText("x"))
        runCurrent()

        timeProvider.advanceBy(10L)
        advanceTimeBy(101)
        runCurrent()

        viewModel.onEvent(GameEvent.OnPauseGame)
        timeProvider.advanceBy(1000L)


        viewModel.onEvent(GameEvent.OnResumeGame)
        runCurrent()
        timeProvider.advanceBy(111L)
        advanceTimeBy(101)
        runCurrent()

        viewModel.stopTimer()

        assertEquals(111L, viewModel.gameState.elapsedTime!!)
    }

    @Test
    fun `finishGame()- TIMEBASED - game ends when elapsed time is equal to TimeBased period and there were no puases`() =
        runTest(testDispatcher) {
            coEvery { scoreRepository.saveGame(any()) } returns Resource.Success(Unit)

            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
            viewModel.onEvent(GameEvent.OnStartGame)
            viewModel.onEvent(GameEvent.OnChangeText("x"))
            runCurrent()

            timeProvider.advanceBy(30000)
            advanceTimeBy(101)
            runCurrent()

            viewModel.stopTimer()

            assertEquals(30000, viewModel.gameState.elapsedTime)
            assertEquals(GameStatus.FINISHED, viewModel.gameState.status)
        }

    @Test
    fun `finishGame() - WORDBASED - game ends when all of words were typed by the user`() =
        runTest(testDispatcher) {
            coEvery { scoreRepository.saveGame(any()) } returns Resource.Success(data = null)

            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleWordMode))
            viewModel.onEvent(GameEvent.OnStartGame)
            runCurrent()

            val buckets = viewModel.gameState.allWords!!


            viewModel.onEvent(GameEvent.OnChangeText(buckets[0][0].toString()))
            runCurrent()
            viewModel.onEvent(GameEvent.OnChangeText(buckets[0].take(buckets[0].length - 1)))
            runCurrent()
            viewModel.onEvent(GameEvent.OnChangeText(buckets[1].take(buckets[1].length - 1)))
            runCurrent()

            assertEquals(GameStatus.FINISHED, viewModel.gameState.status)
        }


    @Test
    fun `OnPlayAgain - restarts stats and puts it into default values, without starting the timer`() =
        runTest(testDispatcher) {
            viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
            viewModel.onEvent(GameEvent.OnStartGame)
            runCurrent()

            timeProvider.advanceBy(30000)
            advanceTimeBy(50)
            runCurrent()
            viewModel.onEvent(GameEvent.OnPlayAgain)

            assertEquals(null, viewModel.gameState.status)
            assertEquals(null, viewModel.gameState.elapsedTime)
        }
}
