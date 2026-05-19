package com.example.keyraceapp.data.mappers

import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity
import com.example.keyraceapp.data.local.entities.UserEntity
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.User
import java.time.LocalDate

fun UserEntity.toUser(): User  = User(name = this.name, id = this.id)
fun User.toUserEntity(): UserEntity = UserEntity(name = this.name)

fun ArcadeScoreEntity.toArcadeScore(): Score.ArcadeScore =
    Score.ArcadeScore(
        wpm = this.wpm,
        acc = this.acc,
        difficulty = Difficulty.stringToDifficulty(this.difficulty),
        points = this.points
    )


fun Score.ArcadeScore.toArcadeScoreEntity(): ArcadeScoreEntity =
    ArcadeScoreEntity(
        wpm = this.wpm,
        acc = this.acc,
        points = this.points,
        difficulty = this.difficulty.toString(),
        date = LocalDate.now().toString()
    )

fun TrainingScoreEntity.toTrainingScore(): Score.TrainingScore =
    Score.TrainingScore(
        wpm = this.wpm,
        correctWords =  this.correctWords,
        acc = this.acc,
        mistakesMade = this.mistakesMade
    )

fun Score.TrainingScore.toTrainingScoreEntity(): TrainingScoreEntity =
    TrainingScoreEntity(
        wpm = this.wpm,
        acc = this.acc,
        correctWords = this.correctWords,
        mistakesMade = this.mistakesMade,
        trainingType = this.trainingType,
        date = LocalDate.now().toString()
    )