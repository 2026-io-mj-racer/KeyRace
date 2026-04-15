package com.example.keyraceapp.domain.models

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD;

    companion object {
        fun stringToDifficulty(s: String): Difficulty  =
            when(s) {
                "EASY" -> EASY
                "MEDIUM" -> MEDIUM
                "HARD" -> HARD
                else -> throw IllegalArgumentException("Invalid String: ${s}; Should be either `EASY` or `MEDIUM` or `HARD`")
            }
    }
}

enum class TimePeriod {
    FIFTEEN_SECONDS,
    THIRTY_SECONDS,
    FORTY_FIVE_SECONDS,
    SIXTY_SECONDS
}

enum class WordCount {
    TEN_WORDS,
    TWENTY_WORDS,
    THIRTY_WORDS,
    FORTY_WORDS,
    FIFTY_WORDS
}

enum class GameStatus {
    PLAYING,
    PAUSED,
    FINISHED
}
