package ru.alexleru.ya.gamesnumbers.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.alexleru.ya.gamesnumbers.R
import ru.alexleru.ya.gamesnumbers.domain.entity.GameResult

@BindingAdapter("requiredScore")
fun bindRequiredScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        score
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percent
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        scorPerscentage(gameResult)
    )
}

private fun scorPerscentage(gameResult: GameResult): Int {
    return if (gameResult.countOfQuestions == 0)
        0
    else
        (gameResult.countOfRightAnswer / gameResult.countOfQuestions.toDouble() * 100).toInt()
}

@BindingAdapter("imgResult")
fun bindImgResult(imageView: ImageView, boolean: Boolean){
    imageView.setImageResource(getImgResource(boolean))
}

private fun getImgResource(boolean: Boolean): Int {
    return if (boolean) R.drawable.ic_launcher_background
    else R.color.purple_200
}