package com.example.keyraceapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.keyraceapp.data.local.entities.UserEntity

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

    @Query("UPDATE userentity SET name = :newName WHERE id = :userId")
    suspend fun updateName(newName: String, userId: String)

}

