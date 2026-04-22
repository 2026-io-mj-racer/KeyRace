package com.example.keyraceapp.domain.models

enum class Difficulty(val value: String = "EASY") {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");


    override fun toString() = this.value

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

enum class TimePeriod(val value: String) {

    FIFTEEN_SECONDS("15"),
    THIRTY_SECONDS("30"),
    FORTY_FIVE_SECONDS("45"),
    SIXTY_SECONDS("60");

    override fun toString() = value
}

enum class WordCount(val value: String) {
    TEN_WORDS("10"),
    TWENTY_WORDS("20"),
    THIRTY_WORDS("30"),
    FORTY_WORDS("40"),
    FIFTY_WORDS("50");
    override fun toString() = value
}

enum class GameStatus {
    PLAYING,
    PAUSED,
    FINISHED
}
