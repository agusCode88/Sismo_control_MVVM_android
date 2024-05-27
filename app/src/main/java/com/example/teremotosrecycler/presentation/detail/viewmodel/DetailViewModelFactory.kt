package com.example.teremotosrecycler.presentation.detail.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teremotosrecycler.domain.TerremotosUseCase

class DetailViewModelFactory(
    private val application: Application,
    private val getTerremotosUseCase: TerremotosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application, getTerremotosUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}