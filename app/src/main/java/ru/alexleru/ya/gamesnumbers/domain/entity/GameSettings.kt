package ru.alexleru.ya.gamesnumbers.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswer: Int,
    val gameTimeInSeconds: Int
)
