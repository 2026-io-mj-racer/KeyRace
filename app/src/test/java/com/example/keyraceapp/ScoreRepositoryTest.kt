package com.example.keyraceapp

import com.example.keyraceapp.data.dao.ArcadeScoreDao
import com.example.keyraceapp.data.dao.TrainingScoreDao
import com.example.keyraceapp.data.dao.UserDao
import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity
import com.example.keyraceapp.data.repositories.ScoreRepositoryImpl
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.util.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class ScoreRepositoryTest {

    val db = mockk<KeyRaceDatabase>()
    val trainingScoreDao = mockk<TrainingScoreDao>()
    val arcadeScoreDao = mockk<ArcadeScoreDao>()
    val userDao = mockk<UserDao>()
    private lateinit var scoreRepository: ScoreRepository

    private val exampleTrainingScoreEntity =
        TrainingScoreEntity(
            wpm = 40f,
            acc = 100f,
            date = LocalDate.MIN,
            correctWords = 40,
            mistakesMade = 0,
            trainingType = "TIME_BASED",
        )

    private val exampleArcadeScoreEntity =
        ArcadeScoreEntity(
            wpm = 50f,
            acc = 100f,
            difficulty = "HARD",
            points = 100,
            date = LocalDate.MIN,
        )

    @Before
    fun setup() {
        every {db.trainingScoreDao } returns trainingScoreDao
        every {db.arcadeScoreDao} returns arcadeScoreDao

        scoreRepository = ScoreRepositoryImpl(db)
    }

    @Test
    fun `ARCADE - getTopTenScores() - should emit Loading and then Success when getting data from mocked DAO`() = runTest {
        coEvery {arcadeScoreDao.getTopTenScores()} returns
                listOf(exampleArcadeScoreEntity)

        val expectedData = listOf(Score.ArcadeScore(
            wpm = 50f,
            acc = 100f,
            difficulty = Difficulty.HARD,
            points = 100
        ))


        val emissions = scoreRepository.getTopTenArcade().toList()

        assertEquals(
            listOf(
                Resource.Loading<Score.ArcadeScore>(),
                Resource.Success(expectedData),
            ),
            emissions
        )
    }

    @Test
    fun `TRAINING - getTopTenScores() - should emit Loading then Success when getting data from mocked DAO`() = runTest {
        coEvery { trainingScoreDao.getTopTenScores() } returns
                listOf(exampleTrainingScoreEntity)

        val expectedData = listOf(Score.TrainingScore(
            wpm = 40f,
            acc = 100f,
            mistakesMade = 0,
            correctWords = 40,
        ))

        val emissions = scoreRepository.getTopTenTraining().toList()

        assertEquals(
            listOf(
                Resource.Loading<Score.TrainingScore>(),
                Resource.Success(expectedData)
            ),
            emissions
        )
    }

    @Test
    fun `ARCADE - getTopTenScores() - should emit Loading then  ERROR and emptyList when getTopTenScores throws an exception`() = runTest {
        coEvery { arcadeScoreDao.getTopTenScores() } throws RuntimeException("Couldn't fetch arcade data")
        val expectedData = emptyList<Score.ArcadeScore>()

        val emissions = scoreRepository.getTopTenArcade().toList()

        assertEquals(
            listOf(
                Resource.Loading<List<Score.ArcadeScore>>(),
                Resource.Error(
                "Couldn't fetch arcade data",
                expectedData

                )),
             emissions
        )

    }

    @Test
    fun `TRAINING - getTopTenScores() - should emit Loading then ERROR and emptyList when getTopTenScores throws an exception`() = runTest {
        coEvery { trainingScoreDao.getTopTenScores() } throws RuntimeException("Couldn't fetch training data")
        val expectedData = emptyList<Score.TrainingScore>()

        val emissions = scoreRepository.getTopTenTraining().toList()

        assertEquals(
            listOf(
                Resource.Loading<List<Score.TrainingScore>>(),
                Resource.Error(
                    "Couldn't fetch training data",
                    expectedData
                )),
            emissions
        )

    }
    @Test
    fun `getTotalWords() - should return Success with total number of words typed by the user`() = runTest {
        coEvery {userDao.getTotalWords()} returns 10L
        val expected = Resource.Success(10L)

        val actual = scoreRepository.getTotalWords()

        assertEquals(expected, actual)
    }
    @Test
    fun `getTotalWords() - should return Error in case of exception with total equal with total equal to 0`() = runTest {
        coEvery {userDao.getTotalWords()} throws RuntimeException("Unable to get total words")
        val expected = Resource.Error<Long>(message = "Unable to get total words")

        val actual = scoreRepository.getTotalWords()

        assertEquals(expected, actual)


    }
    @Test
    fun `getTotalGames() - should return Success with total number of games played by the user`() = runTest {
        coEvery {userDao.getTotalGames()} returns 10L
        val expected = Resource.Success(10L)

        val actual = scoreRepository.getTotalGames()

        assertEquals(expected, actual)
    }
    @Test
    fun `getTotalGames() - should return Error in case of exception with total equal to 0`() = runTest {
        coEvery {userDao.getTotalGames()} throws RuntimeException("Unable to get total games")
        val expected = Resource.Error<Long>(message = "Unable to get total games")

        val actual = scoreRepository.getTotalGames()

        assertEquals(expected, actual)



    }
}