package com.example.teremotosrecycler.presentation.home.viewmodel

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
    private var _filterSaved = MutableLiveData<Boolean>()
    private var sharedPreferences = application.getSharedPreferences("SismosPref",0)
    val terremotoLV: LiveData<MutableList<Terremoto>>
        get() = _terremotoList

    val statusLv: LiveData<TerremotoApiResponseStatus>
        get() = _statusApi

    val filterLv: LiveData<Boolean>
        get() = _orderListFilter


    init{
         getTerremotosBD()
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
                    _statusApi.value = TerremotoApiResponseStatus.DONE
                }

            }catch (e: UnknownHostException){
                Log.e("MainViewModel", "Not Network connection")
                _terremotoList.value = getTerremotosUseCase.getTerremotosFromDatabase()
                _statusApi.value = TerremotoApiResponseStatus.NOT_INTERNET_CONNECTION_ERROR
                _statusApi.value = TerremotoApiResponseStatus.DONE
            }

        }
    }

    suspend fun saveAllTerremotos(listTerremotosApi: MutableList<Terremoto>){
        getTerremotosUseCase.saveTerremotos(listTerremotosApi)
    }

    fun getTerremotosBD(){
        viewModelScope.launch {
            _terremotoList.value = getTerremotosUseCase.getTerremotosFromDatabase()
            if(_terremotoList.value!!.isEmpty()){
                _orderListFilter.value = false
                fetchTerremotos(false)
            }
        }
    }

    fun setOrderListFilter(orderByMagnitude: Boolean) {
        _orderListFilter.value = orderByMagnitude
        fetchTerremotos(orderByMagnitude)
    }

    fun setFilterSharedPreferences(orderByMagnitude: Boolean){
            if(orderByMagnitude) sharedPreferences.edit().putBoolean("byMagnitudeFilter",true).apply()
            else sharedPreferences.edit().putBoolean("byMagnitudeFilter",false).apply()
    }

    fun getFilterSP(): Boolean{
        return sharedPreferences.getBoolean("byMagnitudeFilter",false)
    }

}