package com.example.keyraceapp

import com.example.keyraceapp.domain.models.Difficulty
import com.example.keyraceapp.domain.models.TypingCalculator
import org.junit.Test

import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TypingCalculatorTest {

   @Test
    fun `WPM - should return 40 when length is 100 and time is 30`() {
        val result = TypingCalculator.computeWpm(30f, 100)
        assertEquals(40f, result)
    }
    @Test
    fun `WPM - should return 60 when length is 500 and time is 100`() {
        val result = TypingCalculator.computeWpm(100f, 500)
        assertEquals(60f, result)
    }
    @Test
    fun `WPM - should return 49,20 when length is 123 and time is 30`() {
        val result = TypingCalculator.computeWpm(30f, 123)
        assertEquals(49.20f, result)
    }
    @Test
    fun `WPM - should return 57,17 when length is 111 and time is 23,3`() {
        val result = TypingCalculator.computeWpm(23.3f, 111)
        assertEquals(57.17f, result)
    }
    @Test
    fun `WPM - should return 0 when any parameter is negative`() {
        data class WPMParameters(val length: Int, val time: Float)

        val cases = listOf(
            WPMParameters(-1, 10f),
            WPMParameters(10, -1f)
        )

        cases.forEach {
            val result = TypingCalculator.computeWpm(it.time, it.length)
            assertEquals(message = "Failed for arguments: ${it.length}, ${it.time}", expected = 0f, actual = result)
        }
    }
    @Test
    fun `ACC - should return 88,33 when length is 60 and mistakesMade is 7`() {
        val result = TypingCalculator.computeAcc(60, 7)
        assertEquals(88.33f, result)

    }
    @Test
    fun `ACC - should return 0 when length is 0`() {
        val result = TypingCalculator.computeAcc(0, 0)
        assertEquals(0f, result)
    }
    @Test
    fun `ACC - should return 100 when length is 60 and mistakesMade is 0`() {
        val result = TypingCalculator.computeAcc(60, 0)
        assertEquals(100f, result)
    }
    @Test
    fun `ACC - should return 0 when any parameter is negative`() {
        data class ACCParameters(val length: Int, val mistakesMade: Int)

        val cases = listOf(
            ACCParameters(1, mistakesMade = -1),
            ACCParameters(length = -1, 20)
        )
        cases.forEach {
            val result = TypingCalculator.computeAcc(it.length, it.mistakesMade)
            assertEquals(message = "Failed for arguments: ${it.length}, ${it.mistakesMade}", expected = 0f, actual = result)
        }
    }


    @Test
    fun `Points - should return 10 when len is 5 and difficulty is Medium and wpm is 40 and acc is 100 `() {
        val result = TypingCalculator.computePoints(5 , Difficulty.MEDIUM, 40f, 100f)
        assertEquals(10, result)
    }
    @Test
    fun `Points - should return 16 when len is 8 and  difficulty is Hard and wpm 40 is and acc is 100 `() {
        val result = TypingCalculator.computePoints(8 , Difficulty.HARD, 40f, 100f)
        assertEquals(16, result)
    }
    @Test
    fun `Points - should return 7 when len is 3 and difficulty is Easy  and wpm is 50 and acc is 98`() {
        val result = TypingCalculator.computePoints(3 , Difficulty.EASY, 50f, 98f)
        assertEquals(7, result)
    }
    @Test
    fun `Points - should return 0 when any parameter is 0`() {
        data class PointsParameters(val len: Int, val difficulty: Difficulty, val wpm: Float, val acc: Float)

        val cases = listOf(
            PointsParameters(0, Difficulty.EASY, 10f, 11f),
            PointsParameters(10, Difficulty.EASY, 0f, 11f),
            PointsParameters(10, Difficulty.EASY, 10f, 0f),
        )

        cases.forEach {
            val result = TypingCalculator.computePoints(it.len, it.difficulty, it.wpm, it.acc)
            assertEquals(message = "Failed for arguments: ${it.len}, ${it.difficulty}, ${it.wpm}, ${it.acc}",expected = 0, actual = result)
        }
    }
}