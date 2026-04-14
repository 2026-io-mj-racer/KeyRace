package com.example.keyraceapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity

@Dao
interface ArcadeScoreDao {
    @Insert
    suspend fun insertScore(score: ArcadeScoreEntity)

    @Query("SELECT * FROM arcadescoreentity ORDER BY points DESC LIMIT 10")
    suspend fun getTopTenScores(): List<ArcadeScoreEntity>

}

