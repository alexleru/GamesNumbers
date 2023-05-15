package ru.alexleru.ya.gamesnumbers.domain.entity

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    val rightAnswer = sum - visibleNumber
}
