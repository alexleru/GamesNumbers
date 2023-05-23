package ru.alexleru.ya.gamesnumbers.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        scorePerscentage(gameResult)
    )
}

private fun scorePerscentage(gameResult: GameResult): Int {
    return if (gameResult.countOfQuestions == 0)
        0
    else
        (gameResult.countOfRightAnswer / gameResult.countOfQuestions.toDouble() * 100).toInt()
}

@BindingAdapter("imgResult")
fun bindImgResult(imageView: ImageView, boolean: Boolean) {
    imageView.setImageResource(getImgResource(boolean))
}

private fun getImgResource(boolean: Boolean): Int {
    return if (boolean) R.drawable.ic_launcher_background
    else R.color.purple_200
}

@BindingAdapter("sum")
fun bindTextViewSum(textView: TextView, int: Int) {
    textView.text = int.toString()
}

@BindingAdapter("leftNumber")
fun bindTextViewLeftNumber(textView: TextView, int: Int) {
    textView.text = int.toString()
}

@BindingAdapter("progressColor")
fun bindProgressColor(progressBar: ProgressBar, boolean: Boolean){
    val color = getColorByState(progressBar.context, boolean)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("textColorProgress")
fun bindTextColor(textView: TextView, boolean: Boolean){
    textView.setTextColor(getColorByState(textView.context, boolean))
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

interface ClickOption{
    fun onClick(int: Int)
}

@BindingAdapter("optionClick")
fun bindOptions(textView: TextView, findOption: ClickOption){
    textView.setOnClickListener {
        findOption.onClick(textView.text.toString().toInt())
    }
}