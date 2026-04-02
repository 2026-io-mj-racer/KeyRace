package com.example.keyraceapp

import org.junit.Test

import org.junit.Before
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TypingCalculatorTest {

   @Test
    fun `WPM - should return 40 when length is 100 and time is 30`() {
        val result = TypingCalculator.computeWpm(30, 100)
        assertEquals(40f, result)
    }
    @Test
    fun `WPM - should return 60 when length is 500 and time is 100`() {
        val result = TypingCalculator.computeWpm(100, 500)
        assertEquals(60f, result)
    }
    @Test
    fun `WPM - should return 49,20 when length is 123 and time is 30`() {
        val result = TypingCalculator.computeWpm(30, 123)
        assertEquals(49.20, result)
    }
    @Test
    fun `WPM - should return 57,17 when length is 111 and time is 23,3`() {
        val result = TypingCalculator.computeWpm(23.3, 111)
        assertEquals(57.17, result)
    }
    @Test
    fun `ACC - should return 88,33 when length is 60 and mistakesMade is 7`() {
        val result = TypingCalculator.computeAcc(60, 7)
        assertEquals(88.33, result)

    }
    @Test
    fun `ACC - should return 0 when length is 0`() {
        val result = TypingCalculator.computeAcc(0, 0)
        assertEquals(0, result)
    }
    @Test
    fun `ACC - should return 100 when length is 60 and mistakesMade is 0`() {
        val result = TypingCalculator.computeAcc(60, 0)
        assertEquals(100, result)
    }
    @Test
    fun `ACC - should return 98,5 when length is 200 and mistakesMade is 3`() {
        val result = TypingCalculator.computeAcc(200, 3)
        assertEquals(98.5, result)
    }
    @Test
    fun `Points - should return 10 when len is 5 and difficulty is Medium and wpm is 40 and acc is 100 `() {
        val result = TypingCalculator.computePoints(5 , Difficulty.MEDIUM, 40, 100)
        assertEquals(10, result)()
    }
    @Test
    fun `Points - should return 16 when len is 8 and  difficulty is Hard and wpm 40 is and acc is 100 `() {
        val result = TypingCalculator.computePoints(8 , Difficulty.HARD, 40, 100)
        assertEquals(16, result)
    }
    @Test
    fun `Points - should return 7 when len is 3 and difficulty is Easy  and wpm is 50 and acc is 98`() {
        val result = TypingCalculator.computePoints(3 , Difficulty.EASY, 50, 98)
        assertEquals(7, result)
    }
    @Test
    fun `Points - should return 0 when any parameter is 0`() {
        val cases = listOf(
            Quadruple(0, Difficulty.EASY, 10, 11, 12),
            Quadruple(10, Difficulty.EASY, 0, 11, 12),
            Quadruple(10, Difficulty.EASY, 10, 0, 12),
            Quadruplae(10, Difficulty.EASY, 10, 11, 0),

        )

        cases.forEach { (len, difficulty, wpm, acc, expected): Quadruple ->
            val result = TypingCalculator.computePoints(len, difficulty, wpm, acc)
            assertEquals(message = "Failed for arguments: $len, $difficulty, $wpm, $acc",expected = 0, actual = result)
        }
    }
}