package com.example.teremotosrecycler.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.network.TerremotoApiResponseStatus
import com.example.teremotosrecycler.domain.TerremotosUseCase
import kotlinx.coroutines.launch
import java.net.UnknownHostException


private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(
    application: Application,
    private val getTerremotosUseCase: TerremotosUseCase
) : AndroidViewModel(application) {

    /**
     * Las corrutinas son procesos que podemos indicarle en que Hilos queremos que se
     * ejecuten dentro de nuestra app
     */

    private var _terremotoList= MutableLiveData<MutableList<Terremoto>>()
    private var _statusApi = MutableLiveData<TerremotoApiResponseStatus>()
    val terremotoLV: LiveData<MutableList<Terremoto>>
        get() = _terremotoList

    val statusLv: LiveData<TerremotoApiResponseStatus>
        get() = _statusApi


    init{
        viewModelScope.launch {

           try {
               _statusApi.value = TerremotoApiResponseStatus.LOADING
               val terremotos = getTerremotosUseCase.gelAllEartquakes()
               getTerremotosUseCase.saveTerremotos(terremotos)
               // Load terremotos from database
               _terremotoList.value = getTerremotosUseCase.getTerremotosFromDatabase()
               _statusApi.value = TerremotoApiResponseStatus.DONE


           }catch (e: UnknownHostException){
                Log.e(TAG, "Not Network connection")
               _terremotoList.value = getTerremotosUseCase.getTerremotosFromDatabase()
               _statusApi.value = TerremotoApiResponseStatus.NOT_INTERNET_CONNECTION_ERROR
           }

        }

    }

}
