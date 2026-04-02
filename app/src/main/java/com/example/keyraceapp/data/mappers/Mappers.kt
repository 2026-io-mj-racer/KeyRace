package com.example.keyraceapp.data.mappers

import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity
import com.example.keyraceapp.data.local.entities.UserEntity
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.User

fun UserEntity.toUser(): User {
   return User(name = "Unknown")

}

fun User.toUserEntity(): UserEntity {
    return UserEntity(name = "Unknown")

}

fun ArcadeScoreEntity.toArcadeScore(): Score.ArcadeScore? {
    return null
}

fun Score.ArcadeScore.toArcadeScoreEntity(): ArcadeScoreEntity? {
   return null;
}

fun TrainingScoreEntity.toTrainingScore(): Score.TrainingScore? {
    return null
}

fun Score.TrainingScore.toTrainingScoreEntity(): TrainingScoreEntity? {

    return null

}