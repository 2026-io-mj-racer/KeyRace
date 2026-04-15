package com.example.keyraceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.keyraceapp.data.dao.ArcadeScoreDao
import com.example.keyraceapp.data.dao.TrainingScoreDao
import com.example.keyraceapp.data.dao.UserDao
import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity
import com.example.keyraceapp.data.local.entities.UserEntity


@Database(
    entities = [UserEntity::class, TrainingScoreEntity::class, ArcadeScoreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KeyRaceDatabase: RoomDatabase() {
    abstract val arcadeScoreDao: ArcadeScoreDao
    abstract val userDao: UserDao
    abstract val trainingScoreDao: TrainingScoreDao

}

