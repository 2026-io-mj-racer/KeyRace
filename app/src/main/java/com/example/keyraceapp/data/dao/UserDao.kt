package com.example.keyraceapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity
import com.example.keyraceapp.data.local.entities.UserEntity
import com.example.keyraceapp.domain.models.User

@Dao
interface UserDao {
    @Query("SELECT SUM(correctWords) FROM TrainingScoreEntity")
    suspend fun getTotalWords(): Long

    @Query("SELECT COUNT(*) FROM TrainingScoreEntity")
    suspend fun getTotalGames(): Long

    @Query("DELETE FROM userentity")
    suspend fun deleteUser()
    @Query("DELETE FROM ArcadeScoreEntity")
    suspend fun deleteArcadeScores()
    @Query("DELETE FROM TrainingScoreEntity")
    suspend fun deleteTrainingScores()

    @Query("SELECT * FROM userentity")
    suspend fun getUser(): UserEntity

    @Insert
    suspend fun insert(user: UserEntity)

}

