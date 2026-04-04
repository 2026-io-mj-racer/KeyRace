package com.example.keyraceapp.domain.repositories

import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    suspend fun saveGame(score: Score): Resource<Unit>
    fun getTopTenArcade(): Flow<Resource<List<Score.ArcadeScore>>>
    fun getTopTenTraining(): Flow<Resource<List<Score.TrainingScore>>>
    suspend fun getTotalWords(): Resource<Long>
    suspend fun getTotalGames(): Resource<Long>
}

