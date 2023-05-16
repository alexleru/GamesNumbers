package ru.alexleru.ya.gamesnumbers.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswer: Int,
    val minPercentOfRightAnswer: Int,
    val gameTimeInSeconds: Int
) : Parcelable {

    val minCountOfRightAnswerString: String
        get() = minCountOfRightAnswer.toString()
    val minPercentOfRightAnswerString: String
        get() = minPercentOfRightAnswer.toString()
}
