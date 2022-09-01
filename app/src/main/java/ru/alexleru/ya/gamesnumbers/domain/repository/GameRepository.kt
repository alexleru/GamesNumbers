package ru.alexleru.ya.gamesnumbers.domain.repository

import ru.alexleru.ya.gamesnumbers.domain.entity.GameSettings
import ru.alexleru.ya.gamesnumbers.domain.entity.Level
import ru.alexleru.ya.gamesnumbers.domain.entity.Question

interface GameRepository {

    fun generationGame(maxSumValue: Int, countOfOptions: Int):Question

    fun getGameSetting(level: Level): GameSettings

}