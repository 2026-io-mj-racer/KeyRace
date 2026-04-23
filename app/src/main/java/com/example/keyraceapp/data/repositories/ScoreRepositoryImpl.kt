package com.example.keyraceapp.data.repositories

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.mappers.toArcadeScore
import com.example.keyraceapp.data.mappers.toArcadeScoreEntity
import com.example.keyraceapp.data.mappers.toTrainingScore
import com.example.keyraceapp.data.mappers.toTrainingScoreEntity
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScoreRepositoryImpl(
    private val database: KeyRaceDatabase,
): ScoreRepository {

    val trainingScoreDao = database.trainingScoreDao
    val arcadeScoreDao = database.arcadeScoreDao
    val userDao = database.userDao

    override suspend fun saveGame(score: Score): Resource<Unit> {
        try {
            when(score) {
                is Score.ArcadeScore -> {
                    arcadeScoreDao.insertScore(score.toArcadeScoreEntity())
                }
                is Score.TrainingScore -> {
                    trainingScoreDao.insertScore(score.toTrainingScoreEntity())
                }
            }

            return Resource.Success(Unit)
        } catch(e: Exception) {
            return Resource.Error("Unable to save game")
        }
    }

    override fun getTopTenArcade(): Flow<Resource<List<Score.ArcadeScore>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val result = arcadeScoreDao
                    .getTopTenScores()
                    .map { it.toArcadeScore()}
                //MOCK DATA TO SEE THE UI
/*                val result: List<Score.ArcadeScore> = listOf(
                    Score.ArcadeScore(wpm = 50f, acc = 100f, points = 300),
                    Score.ArcadeScore(wpm = 20f, acc = 100f, points = 160),
                    Score.ArcadeScore(wpm = 10f, acc = 98f, points = 160),
                )*/



                emit(Resource.Success(result))
            } catch(e: Exception) {
                emit(Resource.Error("Couldn't fetch arcade data", data = emptyList()))
            }
        }
    }

    override fun getTopTenTraining(): Flow<Resource<List<Score.TrainingScore>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val result = trainingScoreDao
                    .getTopTenScores()
                    .map { it.toTrainingScore()}


                emit(Resource.Success(result))
            } catch(e: Exception) {
                emit(Resource.Error("Couldn't fetch training data", data = emptyList()))
            }
        }
    }

    override suspend fun getTotalWords(): Resource<Long> {
        return try {
            Resource.Success(userDao.getTotalWords())
        } catch(e: Exception) {
            Resource.Error("Unable to get total words")
        }
    }

    override suspend fun getTotalGames(): Resource<Long> {
        return try {
            Resource.Success(userDao.getTotalGames())
        } catch(e: Exception) {
            Resource.Error("Unable to get total games")
        }
    }

}