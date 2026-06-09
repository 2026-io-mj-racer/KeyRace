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
    fun `WPM - should return 40 when length is 100 and time is 30000`() {
        val result = TypingCalculator.computeWpm(30000f, 100)
        assertEquals(40f, result)
    }
    @Test
    fun `WPM - should return 60 when length is 500 and time is 100000`() {
        val result = TypingCalculator.computeWpm(100000f, 500)
        assertEquals(60f, result)
    }
    @Test
    fun `WPM - should return 49,20 when length is 123 and time is 30000`() {
        val result = TypingCalculator.computeWpm(30000f, 123)
        assertEquals(49.20f, result)
    }
    @Test
    fun `WPM - should return 57,17 when length is 111 and time is 23300`() {
        val result = TypingCalculator.computeWpm(23300f, 111)
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
    fun `Points - should return 0 when len is 0`() {
        val result = TypingCalculator.computePoints(0, Difficulty.EASY)

        assertEquals(0L, result)
    }

    @Test
    fun `Points - should return len when difficulty is Easy`() {
        val result = TypingCalculator.computePoints(10, Difficulty.EASY)

        assertEquals(10L, result)
    }

    @Test
    fun `Points - should return len multiplied by 1_2 when difficulty is Medium`() {
        val result = TypingCalculator.computePoints(10, Difficulty.MEDIUM)

        assertEquals(12L, result)
    }

    @Test
    fun `Points - should return len multiplied by 1_5 when difficulty is Hard`() {
        val result = TypingCalculator.computePoints(10, Difficulty.HARD)

        assertEquals(15L, result)
    }
}
