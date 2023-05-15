package ru.alexleru.ya.gamesnumbers.domain.usecases

import ru.alexleru.ya.gamesnumbers.domain.entity.GameSettings
import ru.alexleru.ya.gamesnumbers.domain.entity.Level
import ru.alexleru.ya.gamesnumbers.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings{
        return repository.getGameSetting(level)
    }

}