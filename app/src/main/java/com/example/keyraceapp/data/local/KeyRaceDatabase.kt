package com.example.keyraceapp.data.local

import com.example.keyraceapp.data.dao.ArcadeScoreDao
import com.example.keyraceapp.data.dao.TrainingScoreDao
import com.example.keyraceapp.data.dao.UserDao
import com.example.keyraceapp.domain.models.Score


abstract class KeyRaceDatabase {
    abstract val arcadeScoreDao: ArcadeScoreDao
    abstract val userDao: UserDao
    abstract val trainingScoreDao: TrainingScoreDao

}

