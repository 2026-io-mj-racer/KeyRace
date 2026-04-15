package com.example.keyraceapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity

@Dao
interface TrainingScoreDao {

    @Insert
    suspend fun insertScore(score: TrainingScoreEntity)

    @Query("SELECT * FROM TrainingScoreEntity ORDER BY wpm DESC LIMIT 10")
    suspend fun getTopTenScores(): List<TrainingScoreEntity>
}

