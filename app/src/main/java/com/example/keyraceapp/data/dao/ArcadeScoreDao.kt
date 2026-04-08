package com.example.keyraceapp.data.dao

import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity

interface ArcadeScoreDao {

    suspend fun insertScore(score: ArcadeScoreEntity)
    suspend fun getTopTenScores(): List<ArcadeScoreEntity>

}

