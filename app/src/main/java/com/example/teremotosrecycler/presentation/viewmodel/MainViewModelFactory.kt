package com.example.teremotosrecycler.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teremotosrecycler.domain.TerremotosUseCase

class MainViewModelFactory(private val getTerremotosUseCase: TerremotosUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getTerremotosUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}