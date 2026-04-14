package com.example.keyraceapp

import com.example.keyraceapp.data.local.entities.ArcadeScoreEntity
import com.example.keyraceapp.data.local.entities.TrainingScoreEntity
import com.example.keyraceapp.data.local.entities.UserEntity
import com.example.keyraceapp.data.mappers.toArcadeScore
import com.example.keyraceapp.data.mappers.toArcadeScoreEntity
import com.example.keyraceapp.data.mappers.toTrainingScore
import com.example.keyraceapp.data.mappers.toTrainingScoreEntity
import com.example.keyraceapp.data.mappers.toUser
import com.example.keyraceapp.data.mappers.toUserEntity
import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.Score
import com.example.keyraceapp.domain.models.User
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDate
import kotlin.test.assertEquals

class MappersTest {

    private val matchingTrainingScoreEntity =
        TrainingScoreEntity(
            wpm = 20f,
            acc = 100f,
            date = LocalDate.MIN.toString(),
            correctWords = 30,
            mistakesMade = 0,
            trainingType = "TIME_BASED",
        )

    private val matchingTrainingScore =
        Score.TrainingScore(
            wpm = 20f,
            acc = 100f,
            correctWords = 30,
            mistakesMade = 0,
        )

    private val matchingArcadeScoreEntity =
        ArcadeScoreEntity(
            wpm = 10f,
            acc = 100f,
            difficulty = "MEDIUM",
            points = 10,
            date = LocalDate.MIN.toString(),
        )

    private val matchingArcadeScore =
        Score.ArcadeScore(
            wpm = 10f,
            acc = 100f,
            difficulty = Difficulty.MEDIUM,
            points = 10,
        )

    private val matchingUserEntity =
        UserEntity(
            name = "John"
        )

    private val matchingUser =
        User(
            name = "John"
        )
    private val userPair =
        matchingUserEntity to matchingUser

    private val trainingScorePair =
        matchingTrainingScoreEntity to matchingTrainingScore

    private val arcadeScorePair =
        matchingArcadeScoreEntity to matchingArcadeScore


    @Test
    fun `UserEntity maps correctly to User`() {
        val (entity, domain) = userPair

        val actual = entity.toUser()

        assertEquals(domain, actual)
    }
    @Test
    fun `User maps correctly to UserEntity`() {
        val (entity, domain) = userPair

        val actual = domain.toUserEntity()

        assertEquals(entity, actual)
    }
    @Test
    fun `ArcadeScoreEntity maps correctly to ArcadeScore`() {
        val (entity, domain) = arcadeScorePair

        assertEquals(domain, entity.toArcadeScore())
    }

    @Test
    fun `ArcadeScore maps correctly to ArcadeScoreEntity`() {

        val (entity, domain) = arcadeScorePair

        expectThat(domain.toArcadeScoreEntity()!!){
            get{wpm}.isEqualTo(entity.wpm)
            get{acc}.isEqualTo(entity.acc)
            get{points}.isEqualTo(entity.points)
            get{difficulty}.isEqualTo(entity.difficulty)
        }
    }


    @Test
    fun `TrainingScoreEntity maps correctly to TrainingScore`() {
        val (entity, domain) = trainingScorePair

        assertEquals(domain, entity.toTrainingScore())

    }
    @Test
    fun `TrainingScore maps correctly to TrainingScoreEntity`() {
        val (entity, domain) = trainingScorePair

        expectThat(domain.toTrainingScoreEntity()!!){
            get{wpm}.isEqualTo(entity.wpm)
            get{acc}.isEqualTo(entity.acc)
            get{mistakesMade}.isEqualTo(entity.mistakesMade)
            get{correctWords}.isEqualTo(entity.correctWords)
        }

    }
}