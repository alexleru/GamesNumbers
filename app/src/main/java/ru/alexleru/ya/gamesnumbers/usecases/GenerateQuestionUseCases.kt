package ru.alexleru.ya.gamesnumbers.usecases

import ru.alexleru.ya.gamesnumbers.domain.entity.Question
import ru.alexleru.ya.gamesnumbers.domain.repository.GameRepository

class GenerateQuestionUseCases(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generationGame(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}