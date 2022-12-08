package ru.alexleru.ya.gamesnumbers.data

import ru.alexleru.ya.gamesnumbers.domain.entity.GameSettings
import ru.alexleru.ya.gamesnumbers.domain.entity.Level
import ru.alexleru.ya.gamesnumbers.domain.entity.Question
import ru.alexleru.ya.gamesnumbers.domain.repository.GameRepository
import kotlin.math.min
import kotlin.math.max
import java.util.HashSet
import kotlin.random.Random

object GameRepositoryImpl : GameRepository{

    private val MIN_VALUE_FOR_SUM = 2
    private val MIN_VALUE_FOR_VISIBLE_NUMBER = 1

    override fun generationGame(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_VALUE_FOR_SUM, maxSumValue)
        val visibleNumber = Random.nextInt(MIN_VALUE_FOR_VISIBLE_NUMBER, sum)

        val setOfOptions = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        setOfOptions.add(rightAnswer)
        val minValueOption = max(MIN_VALUE_FOR_VISIBLE_NUMBER, rightAnswer - countOfOptions)
        val maxValueOption = min(maxSumValue, rightAnswer + countOfOptions)
        while (setOfOptions.size < countOfOptions){
            val option = Random.nextInt(minValueOption, maxValueOption)
            setOfOptions.add(option)
        }
        return Question(sum, visibleNumber, setOfOptions.toList())
    }

    override fun getGameSetting(level: Level): GameSettings {
        return when (level){
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                10
            )
            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )
            Level.NORMAL -> GameSettings(
                20,
                10,
                80,
                50
            )
            Level.HARD -> GameSettings(
                30,
                10,
                90,
                40
            )
        }
    }
}