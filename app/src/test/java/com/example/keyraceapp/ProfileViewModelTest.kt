package com.example.keyraceapp

import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.User
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.domain.repositories.UserRepository
import com.example.keyraceapp.presentation.UserProfile.ProfileEvent
import com.example.keyraceapp.presentation.UserProfile.ProfileState
import com.example.keyraceapp.presentation.UserProfile.ProfileViewModel
import com.example.keyraceapp.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProfileViewModelTest {
    private lateinit var viewModel: ProfileViewModel
    private val userRepository = mockk<UserRepository>()
    private val scoreRepository = mockk<ScoreRepository>()

    @Before
    fun setUp() {
        viewModel = ProfileViewModel(userRepository, scoreRepository)
    }

    // SUCCESS SCENARIO
    @Test
    fun `fetchUser - sets username in state when userRepository returns success` () = runTest {
        val expectedUser = User("John")
        coEvery { userRepository.getUser() } returns Resource.Success(expectedUser)

        viewModel.onEvent(ProfileEvent.OnFetchUser)

        assert(viewModel.state.user == expectedUser)
    }

    @Test
    fun `fetchTrainingData - sets topWpm in state when userRepository returns success`() = runTest {
        val expectedWpm = 50f
        coEvery { scoreRepository.getTopTenTraining() } returns
                flowOf(
                    Resource.Success(
                        listOf(
                            Score.TrainingScore(
                                correctWords = 100, wpm = expectedWpm
                            )
                        )
                    )
                )

        viewModel.onEvent(ProfileEvent.OnFetchTraining)

        assert(viewModel.state.topWpm == expectedWpm)
    }

   @Test
    fun `fetchTrainingData - sets topScores in state when userRepository returns success`() = runTest {
        val expectedScores = listOf(
            Score.TrainingScore(correctWords = 100, wpm = 50f, acc = 100f, mistakesMade = 0),
            Score.TrainingScore(correctWords = 100, wpm = 30f, acc = 100f, mistakesMade = 0),
            Score.TrainingScore(correctWords = 100, wpm = 20f, acc = 100f, mistakesMade = 0)
        )
        coEvery { scoreRepository.getTopTenTraining() } returns
                flowOf(
                    Resource.Success(
                        expectedScores
                    )
                )

        viewModel.onEvent(ProfileEvent.OnFetchTraining)

        assert(viewModel.state.topScores == expectedScores)
    }

    @Test
    fun `fetchTrainingData - sets topScores to emptyList when userRepository return success and emptyList`() = runTest {
        coEvery { scoreRepository.getTopTenTraining() } returns
                flowOf(
                    Resource.Success(
                        emptyList()
                    )
                )

        viewModel.onEvent(ProfileEvent.OnFetchTraining)

        assert(viewModel.state.topScores.isEmpty())
    }

    @Test
    fun  `fetchTrainingData - sets wordsTyped in state when userRepository returns success` () = runTest {
        coEvery { scoreRepository.getTotalWords() } returns Resource.Success(2000)

        viewModel.onEvent(ProfileEvent.OnFetchTraining)

        assert(viewModel.state.wordsTyped == 2000)
    }

    @Test
    fun `fetchTrainingData - sets gamesPlayed in state when userRepository return success`() = runTest {
        coEvery { scoreRepository.getTotalGames() } returns Resource.Success(100)

        viewModel.onEvent(ProfileEvent.OnFetchTraining)

        assert(viewModel.state.gamesPlayed == 100)

    }

    @Test
    fun `fetchArcadeData - sets topWpm in state when userRepository returns success`() = runTest {
        val expectedWpm = 60f
        coEvery { scoreRepository.getTopTenArcade() } returns
                flowOf(
                    Resource.Success(
                        listOf(Score.ArcadeScore(wpm = expectedWpm))
                    )
                )

        viewModel.onEvent(ProfileEvent.OnFetchArcade)

        assert(viewModel.state.topWpm == expectedWpm)
    }


    @Test
    fun `fetchArcadeData - sets topScores in state when userRepository returns success`() = runTest {
        val expectedScores = listOf(
            Score.ArcadeScore(wpm = 60f, acc = 100f, difficulty = Difficulty.EASY),
            Score.ArcadeScore(wpm = 50f, acc = 100f, difficulty = Difficulty.EASY),
            Score.ArcadeScore(wpm = 40f, acc = 100f, difficulty = Difficulty.EASY)
        )
        coEvery { scoreRepository.getTopTenArcade() } returns
                flowOf (
                    Resource.Success(expectedScores)
                )

        viewModel.onEvent(ProfileEvent.OnFetchArcade)

        assert(viewModel.state.topScores == expectedScores)
    }

    @Test
    fun `fetchArcadeData - sets topScores to emptyList when userRepository return success and emptyList`() = runTest {

        coEvery {scoreRepository.getTopTenArcade()} returns
                flowOf(
                    Resource.Success(emptyList())
                )

        viewModel.onEvent(ProfileEvent.OnFetchArcade)

        assert(viewModel.state.topScores.isEmpty())
    }

    @Test
    fun `fetchArcadeData - sets wordsTyped in state when userRepository returns success`() = runTest {
        coEvery { scoreRepository.getTotalWords() } returns Resource.Success(2000)

        viewModel.onEvent(ProfileEvent.OnFetchArcade)

        assert(viewModel.state.wordsTyped == 2000)

    }

    @Test
    fun `fetchGamesPlayed - sets gamesPlayed in state when userRepository returns success`() = runTest {

        coEvery { scoreRepository.getTotalGames() } returns Resource.Success(100)

        viewModel.onEvent(ProfileEvent.OnFetchArcade)

        assert(viewModel.state.gamesPlayed == 100)
    }
    @Test
    fun `OnResetUserData - sets state to initial values after reset when userRepository returns success`() = runTest {
        val user = User("John")
        coEvery { userRepository.resetData(user) } returns Resource.Success(Unit)

        viewModel.onEvent(ProfileEvent.OnResetUserData)

        assert(viewModel.state == ProfileState())
    }

    // ERROR SCENARIO
    @Test
    fun `OnFetchTrainingData - sets errorMessage when userRepository returns error`() = runTest {
        coEvery { scoreRepository.getTopTenTraining() } returns
                flowOf(
                    Resource.Error("Unable to fetch training data")
                )

        viewModel.onEvent(ProfileEvent.OnFetchTraining)

        assert(viewModel.state.errorMessage == "Unable to fetch training data")
    }

    @Test
    fun `OnFetchUser - sets errorMessage in state when userRepository returns error`() = runTest {

        coEvery { userRepository.getUser() } returns Resource.Error("Unable to fetch user")

        viewModel.onEvent(ProfileEvent.OnFetchUser)

        assert(viewModel.state.errorMessage == "Unable to fetch user")
    }

    @Test
    fun `OnFetchArcadeData - sets errorMessage in state when userRepository returns error` () = runTest {
        coEvery { scoreRepository.getTopTenArcade() } returns
                flowOf (
                    Resource.Error(message =  "Unable to fetch arcade data")
                )

        viewModel.onEvent(ProfileEvent.OnFetchArcade)

        assert(viewModel.state.errorMessage == "Unable to fetch arcade data")
    }

    @Test
    fun `OnResetUserData - sets errorMessage in state when userRepository returns error` () = runTest {

        val user = User("John")
        coEvery { userRepository.resetData(user) } returns Resource.Error("Unable to reset data")

        viewModel.onEvent(ProfileEvent.OnResetUserData)

        assert(viewModel.state.errorMessage == "Unable to reset data")
    }



}