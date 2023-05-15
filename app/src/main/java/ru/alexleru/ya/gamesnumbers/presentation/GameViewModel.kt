package ru.alexleru.ya.gamesnumbers.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.alexleru.ya.gamesnumbers.R
import ru.alexleru.ya.gamesnumbers.data.GameRepositoryImpl
import ru.alexleru.ya.gamesnumbers.domain.entity.GameResult
import ru.alexleru.ya.gamesnumbers.domain.entity.GameSettings
import ru.alexleru.ya.gamesnumbers.domain.entity.Level
import ru.alexleru.ya.gamesnumbers.domain.entity.Question
import ru.alexleru.ya.gamesnumbers.domain.usecases.GenerateQuestionUseCases
import ru.alexleru.ya.gamesnumbers.domain.usecases.GetGameSettingsUseCase

class GameViewModel(private val level: Level, private val application: Application) : ViewModel() {

    private var context = application

    private var timer: CountDownTimer? = null

    private val gameRepository = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)
    private val generateQuestionUseCases = GenerateQuestionUseCases(gameRepository)

    private lateinit var gameSettings: GameSettings

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }

    fun startGame() {
        getGameSettingsLevel(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswer
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswer
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSettingsLevel(level: Level) {
        gameSettings = getGameSettingsUseCase.invoke(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCases.invoke(gameSettings.maxSumValue)

    }

    private fun startTimer() {
        timer = object :
            CountDownTimer(
                gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND,
                MILLIS_IN_SECOND
            ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formattedTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        (timer as CountDownTimer).start()
    }

    private fun formattedTime(millisUntilFinished: Long): String {
        val sec = millisUntilFinished / MILLIS_IN_SECOND
        val min = sec / SECONDS_IN_MINUTE
        val secLeft = sec - min * SECONDS_IN_MINUTE
        return String.format("%02d : %02d", min, secLeft)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }

}