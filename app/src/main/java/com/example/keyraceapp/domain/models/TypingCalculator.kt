package com.example.keyraceapp.domain.models

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round

object TypingCalculator {
    fun computePoints(len: Int, difficulty: Difficulty, wpm: Float, acc: Float): Long {

        if(wpm == 0f || acc == 0f || len == 0) return 0L

        val multiplier =
            when(difficulty) {
                Difficulty.EASY -> 1.0
                Difficulty.MEDIUM -> 1.2
                Difficulty.HARD -> 1.5
            }

        val accPercent = acc / 100.0

        return (len * multiplier + (accPercent * (wpm / 10))).toLong()
    }
    fun computeWpm(elapsedTime: Float, length: Int): Float  {
        if(elapsedTime <= 0 || length <= 0) return 0f

        val timeInMinutes: Double = (elapsedTime / 1000.0)  / 60.0
        val wordCount: Double = length / 5.0

        val wpm = wordCount / timeInMinutes

        return BigDecimal(wordCount / timeInMinutes)
            .setScale(2, RoundingMode.HALF_EVEN)
            .toFloat()
    }

    fun computeAcc(length: Int, mistakesMade: Int): Float {
        if(length <= 0 || mistakesMade < 0) return 0f
        val lengthWithoutMistakes = length - mistakesMade
        val result: Double = (lengthWithoutMistakes * 100.0) / length.toDouble()

        return BigDecimal(result)
            .setScale(2, RoundingMode.HALF_EVEN)
            .toFloat()
    }
}