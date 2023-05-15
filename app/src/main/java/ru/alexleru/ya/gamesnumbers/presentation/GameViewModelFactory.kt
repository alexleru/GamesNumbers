package ru.alexleru.ya.gamesnumbers.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexleru.ya.gamesnumbers.domain.entity.Level
import java.lang.RuntimeException

class GameViewModelFactory(private val level: Level, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass == GameViewModel::class.java) {
            return GameViewModel(level, application) as T
        }
        throw RuntimeException("Unknown class for GameViewModelFactory $modelClass")
    }
}