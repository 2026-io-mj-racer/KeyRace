package com.example.keyraceapp.domain.models

object TypingCalculator {
    fun computePoints(len: Int, difficulty: Difficulty, wpm: Float, acc: Float): Long {
        return Long.MIN_VALUE
    }
    fun computeWpm(elapsedTime: Float, length: Int): Float {
       return Float.MIN_VALUE
    }

    fun computeAcc(length: Int, mistakesMade: Int): Float {
       return Float.MIN_VALUE
    }
}