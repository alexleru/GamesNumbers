package ru.alexleru.ya.gamesnumbers.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswer: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable {
    @IgnoredOnParcel
    val scorePercentage: Int
        get() {
           return if (countOfQuestions == 0)
                0
            else
                (countOfRightAnswer / countOfQuestions.toDouble() * 100).toInt()
        }

}
