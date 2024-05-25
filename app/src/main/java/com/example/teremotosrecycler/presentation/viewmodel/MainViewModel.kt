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
    private var _orderListFilter = MutableLiveData<Boolean>()
    val terremotoLV: LiveData<MutableList<Terremoto>>
        get() = _terremotoList

    val statusLv: LiveData<TerremotoApiResponseStatus>
        get() = _statusApi

    val filterLv: LiveData<Boolean>
        get() = _orderListFilter


    init{
        _orderListFilter.value = false
        fetchTerremotos(false)

    }

    private fun fetchTerremotos(orderByMagnitude: Boolean){
        viewModelScope.launch {

            try {
                _statusApi.value = TerremotoApiResponseStatus.LOADING
                val terremotos = getTerremotosUseCase.gelAllEartquakes(orderByMagnitude)
                _statusApi.value = TerremotoApiResponseStatus.DONE
                if(terremotos.isNotEmpty()){
                    _terremotoList.value = terremotos
                    saveAllTerremotos(terremotos)
                }else{
                    _terremotoList.value = getTerremotosUseCase.getTerremotosFromDatabase()
                }
                // Load terremotos from database
            }catch (e: UnknownHostException){
                Log.e(TAG, "Not Network connection")
                _terremotoList.value = getTerremotosUseCase.getTerremotosFromDatabase()
                _statusApi.value = TerremotoApiResponseStatus.NOT_INTERNET_CONNECTION_ERROR
            }

        }
    }

    suspend fun saveAllTerremotos(listTerremotosApi: MutableList<Terremoto>){
        getTerremotosUseCase.saveTerremotos(listTerremotosApi)
    }

    fun setOrderListFilter(orderByMagnitude: Boolean) {
        _orderListFilter.value = orderByMagnitude
        fetchTerremotos(orderByMagnitude)
    }


}
