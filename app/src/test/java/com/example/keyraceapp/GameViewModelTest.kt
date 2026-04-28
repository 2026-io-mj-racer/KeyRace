package com.example.keyraceapp

import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.GameMode
import com.example.keyraceapp.domain.models.GameStatus
import com.example.keyraceapp.domain.models.TimePeriod
import com.example.keyraceapp.domain.models.WordCount
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.presentation.Game.ConfigState
import com.example.keyraceapp.presentation.Game.GameEvent
import com.example.keyraceapp.presentation.Game.GameState
import com.example.keyraceapp.presentation.Game.GameViewModel
import com.example.keyraceapp.util.Resource
import com.example.keyraceapp.util.TimeProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.assertEquals

class FakeTimeProvider: TimeProvider {
    var currentTime = 0L
    override fun now(): Long =  currentTime
    fun advanceBy(time: Long) =  currentTime + time;
}
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private var scoreRepository = mockk<ScoreRepository>()
    private var wordRepository = mockk<WordRepository>()
    private val timeProvider = FakeTimeProvider()
    private val testDispatcher = StandardTestDispatcher()
    val exampleTimeMode = GameMode.Training.TimeBased(TimePeriod.THIRTY_SECONDS)
    val exampleWordMode = GameMode.Training.WordBased(WordCount.THIRTY_WORDS)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel = GameViewModel(
            scoreRepository = scoreRepository,
            wordRepository = wordRepository,
            timeProvider =  timeProvider
        )

        coEvery { wordRepository.getWords() } returns flowOf(Resource.Success(listOf("HA", "HI")))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `restartGame() - TIMEBASED - set state into initial values except the target text after restart`() = runTest {

        val expectedGameState = GameState(
            targetText = "HA HI",
            elapsedTime = 0,
            correctWords = 0,
            mistakesMade = 0,
            status = GameStatus.PLAYING,
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
            get {targetText!!.toSortedSet()}.isEqualTo(expectedGameState.targetText!!.toSortedSet())
            get {points }.isEqualTo(expectedGameState.points)
            get {fallingSpeed}.isEqualTo(expectedGameState.fallingSpeed)
            get {status}.isEqualTo(expectedGameState.status)
            get {elapsedTime}.isEqualTo(expectedGameState.elapsedTime)
            get {currentAcc}.isEqualTo(expectedGameState.currentAcc)
            get {mistakesMade}.isEqualTo(expectedGameState.mistakesMade)
            get {lives}.isEqualTo(expectedGameState.lives)
            get {typedText}.isEqualTo(expectedGameState.typedText)
            get {correctWords}.isEqualTo(expectedGameState.correctWords)
            get {currentWpm}.isEqualTo(expectedGameState.currentWpm)
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

        assertEquals(expectedConfigState, viewModel.configState)

    }
    @Test
    fun `startGame() - TIMEBASED - timer starts after user types the first letter and advances the elapsedTime in state`() = runTest {


        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        viewModel.onEvent(GameEvent.OnChangeText("a"))
        //here starTick job maybe scheduled but not ran so we run it manually
        runCurrent()

        //now after starting the clock lets advance our fake timer to simulate the gameplay
        timeProvider.advanceBy(2000)
        //now we also need to advance to Dispatcher clock so our routine is run
        advanceTimeBy(50)
        //again run scheduled coroutines BECAUSE WE NEED TO SEE THE CHANGE IN TIME
        runCurrent()

        assertEquals(2000L, viewModel.gameState.elapsedTime)
    }
    @Test
    fun `startGame() - TIMEBASED - timer does not start after user clicked Start but didnt type the first letter`() = runTest {

        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        runCurrent() //see if timer starts - it should NOT

        assertEquals(0, viewModel.gameState.elapsedTime)

    }

    @Test
    fun `startGame() - sets each of the state values to GameState based on ConfigState when OnStartGame invoked`() = runTest {
        val expectedGameState = GameState(
            targetText = "HA HI",
            elapsedTime = 0,
            correctWords = 0,
            mistakesMade = 0,
            status = GameStatus.PLAYING,
            typedText = "",
        )
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))


        viewModel.onEvent(GameEvent.OnStartGame)


        //assert one by one because of converting targetText to sortedSet
        expectThat(viewModel.gameState) {
            get {targetText!!.toSortedSet()}.isEqualTo(expectedGameState.targetText!!.toSortedSet())
            get {points }.isEqualTo(expectedGameState.points)
            get {fallingSpeed}.isEqualTo(expectedGameState.fallingSpeed)
            get {status}.isEqualTo(expectedGameState.status)
            get {elapsedTime}.isEqualTo(expectedGameState.elapsedTime)
            get {currentAcc}.isEqualTo(expectedGameState.currentAcc)
            get {mistakesMade}.isEqualTo(expectedGameState.mistakesMade)
            get {lives}.isEqualTo(expectedGameState.lives)
            get {typedText}.isEqualTo(expectedGameState.typedText)
            get {correctWords}.isEqualTo(expectedGameState.correctWords)
            get {currentWpm}.isEqualTo(expectedGameState.currentWpm)
        }
   }

    @Test
    fun `updateTyping() - updates correctly the number of mistakes and words typed when user makes only mistakes`() = runTest {

        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        val expectedMistakes = 3
        val expectedCorrectWords = 0

        viewModel.onEvent(GameEvent.OnChangeText("bla"))

        expectThat(viewModel.gameState) {
            get {mistakesMade}.isEqualTo(expectedMistakes)
            get {correctWords}.isEqualTo(expectedCorrectWords)
        }
    }
    @Test
    fun `updateTyping() - updates correctly the number of mistakes and words typed when user type correct words`() = runTest {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        val expectedMistakes = 0
        val expectedCorrectWords = 1
        val expectedTargetText = viewModel.gameState.targetText

        //WHY ADDING " " ??? because counting words after users TYPES THE SPACE!!!!!!!!!!!
        viewModel.onEvent(GameEvent.OnChangeText(expectedTargetText!!.substringBefore(" ") + " "))


        expectThat(viewModel.gameState) {
            get {mistakesMade}.isEqualTo(expectedMistakes)
            get {correctWords}.isEqualTo(expectedCorrectWords)
        }
    }


    @Test
    fun `pauseGame() - set status to PAUSED when OnGamePause invoked`() {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)

        viewModel.onEvent(GameEvent.OnPauseGame)

        assertEquals(GameStatus.PAUSED, viewModel.gameState.status)
    }

    @Test
    fun `pauseGame() - pauses the timer when OnGamePause invoked`() = runTest {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        runCurrent()

        timeProvider.advanceBy(3000L)
        advanceTimeBy(50)
        runCurrent()

        viewModel.onEvent(GameEvent.OnPauseGame)
        timeProvider.advanceBy(1000L)

        assertEquals(3000L, viewModel.gameState.elapsedTime)
    }

    @Test
    fun `resumeGame() sets status to PLAYING when OnResume is invoked`() {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        viewModel.onEvent(GameEvent.OnPauseGame)

        viewModel.onEvent(GameEvent.OnResumeGame)

        assertEquals(GameStatus.PLAYING, viewModel.gameState.status)

    }

    @Test
    fun `resumeGame() resumes timer when OnResume is invoked`() = runTest {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        runCurrent()

        timeProvider.advanceBy(10L)
        advanceTimeBy(50)
        runCurrent()

        viewModel.onEvent(GameEvent.OnPauseGame)
        timeProvider.advanceBy(1000L)


        viewModel.onEvent(GameEvent.OnResumeGame)
        runCurrent()
        timeProvider.advanceBy(10L)
        advanceTimeBy(50)
        runCurrent()

        assertEquals(20L, viewModel.gameState.elapsedTime)
    }

    @Test
    fun `finishGame()- TIMEBASED - game ends when elapsed time is equal to TimeBased period and there were no puases`() = runTest {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        runCurrent()

        timeProvider.advanceBy(30000)
        advanceTimeBy(50)
        runCurrent()

        assertEquals(30000, viewModel.gameState.elapsedTime)
        assertEquals(GameStatus.FINISHED, viewModel.gameState.status)
    }

    @Test
    fun `finishGame() - WORDBASED - game ends when all of words were typed by the user`() = runTest {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleWordMode))
        viewModel.onEvent(GameEvent.OnStartGame)

        viewModel.onEvent(GameEvent.OnChangeText("HA HI"))

        assertEquals(GameStatus.FINISHED, viewModel.gameState.status)
    }


    @Test
    fun `OnPlayAgain - game generates new text and puts it into playing mode, without starting the timer`() = runTest {
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleTimeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        runCurrent()

        timeProvider.advanceBy(30000)
        advanceTimeBy(50)
        runCurrent()
        viewModel.onEvent(GameEvent.OnPlayAgain)

        assertEquals(GameStatus.PLAYING, viewModel.gameState.status)
        assertEquals(0, viewModel.gameState.elapsedTime)
        assertEquals("HA HI".toSortedSet(), viewModel.gameState.targetText!!.toSortedSet(), message = "Check if target text IS NOT NULL!!!")
    }
    @Test
    fun `finishGame() - ARCADE - game end when lives == 0`() = runTest {
        val exampleArcadeMode = GameMode.Arcade(Difficulty.HARD)
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleArcadeMode))
        viewModel.onEvent(GameEvent.OnStartGame)
        runCurrent()

        viewModel.onEvent(GameEvent.OnChangeText("x"))

        assertEquals(0, viewModel.gameState.lives)
        assertEquals(GameStatus.FINISHED, viewModel.gameState.status)
    }

    @Test
    fun `OnChangeText - ARCADE - when user types wrong letter number of lives should be decremented`() = runTest {
        val exampleArcadeMode = GameMode.Arcade(Difficulty.MEDIUM)
        viewModel.onEvent(GameEvent.OnSelectedGameMode(exampleArcadeMode))
        viewModel.onEvent(GameEvent.OnStartGame)

        assertEquals(3, viewModel.gameState.lives)

        viewModel.onEvent(GameEvent.OnChangeText("x"))

        assertEquals(2, viewModel.gameState.lives)


    }
}