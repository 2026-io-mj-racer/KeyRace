package com.example.keyraceapp.data.repositories

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.repositories.ScoreRepository
import com.example.keyraceapp.util.Resource
import kotlinx.coroutines.flow.Flow

class ScoreRepositoryImpl(
    private val database: KeyRaceDatabase,
): ScoreRepository {
    override suspend fun saveGame(score: Score): Resource<Unit> {
        TODO("Not yet implemented")
    }

    override fun getTopTenArcade(): Flow<Resource<List<Score.ArcadeScore>>> {
        TODO("Not yet implemented")
    }

    override fun getTopTenTraining(): Flow<Resource<List<Score.TrainingScore>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalWords(): Resource<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalGames(): Resource<Long> {
        TODO("Not yet implemented")
    }

}