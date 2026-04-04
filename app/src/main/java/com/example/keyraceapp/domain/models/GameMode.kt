package com.example.keyraceapp.domain.models

sealed class GameMode {
   sealed class Training: GameMode() {
       data class TimeBased(val time: TimePeriod): Training()
       data class WordBased(val wordCount: WordCount): Training()
   }

    data class Arcade(val difficulty: Difficulty): GameMode()
}

