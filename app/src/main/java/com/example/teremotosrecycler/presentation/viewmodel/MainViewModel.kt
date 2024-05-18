package com.example.teremotosrecycler.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.model.response.Properties
import com.example.teremotosrecycler.data.model.response.TerremotoResponse
import com.example.teremotosrecycler.data.network.TerremotoApiService
import com.example.teremotosrecycler.data.repository.MainRepository
import com.example.teremotosrecycler.domain.TerremotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val getTerremotosUseCase: TerremotosUseCase): ViewModel() {

    /**
     * Las corrutinas son procesos que podemos indicarle en que Hilos queremos que se
     * ejecuten dentro de nuestra app
     */

    private var _terremotoList= MutableLiveData<MutableList<Terremoto>>()
    val terremotoLV: LiveData<MutableList<Terremoto>>
        get() = _terremotoList

    private val repository = MainRepository()

    init{
        viewModelScope.launch {
            // Actualizo en la corrutina Main no la interior
            _terremotoList.value = getTerremotosUseCase.gelAllEartquakes()
        }

    }

}
