package com.example.keyraceapp.data.dao

import com.example.keyraceapp.data.local.entities.TrainingScoreEntity

interface TrainingScoreDao {

    suspend fun insertScore(score: TrainingScoreEntity)
    suspend fun getTopTenScores(): List<TrainingScoreEntity>

}

