package com.example.keyraceapp.domain.repositories

import com.example.keyraceapp.domain.models.Score

interface ScoreRepository {
    fun saveGame(score: Score)
    fun getTopTenArcade(): List<ArcadeScore>
    fun getTopTenTraining(): List<TrainingScore>
    fun getTotalWords(): Long
    fun getTotalGames(): Long
}

