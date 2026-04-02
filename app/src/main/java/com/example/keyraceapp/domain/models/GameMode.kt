package com.example.keyraceapp.domain.models

sealed class GameMode {
   sealed class Training {
       data class TimeBased(val time: TimePeriod)
       data class WordBased(val wordCount: WordCount)
   }

    data class Arcade(val difficulty: Difficulty)
}

