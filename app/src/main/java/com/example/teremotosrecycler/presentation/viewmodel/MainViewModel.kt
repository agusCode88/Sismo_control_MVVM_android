package com.example.teremotosrecycler.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.model.response.Properties
import com.example.teremotosrecycler.data.model.response.TerremotoResponse
import com.example.teremotosrecycler.data.network.TerremotoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    /**
     * Las corrutinas son procesos que podemos indicarle en que Hilos queremos que se
     * ejecuten dentro de nuestra app
     */

    private var _terremotoList = MutableLiveData<MutableList<Terremoto>>()
    private var serviceApi = TerremotoApiService()


    val terremotoLV: LiveData<MutableList<Terremoto>>
        get() = _terremotoList

    init {
        viewModelScope.launch {
            // Actualizo en la corrutina Main no la interior
            _terremotoList.value = fetchTerremotos()
        }

    }


    // Cuando tenemos una corrutina en un método , hay que utilizar el suspend
    private suspend fun fetchTerremotos(): MutableList<Terremoto> {

        return withContext(Dispatchers.IO) {
            val terremotos: TerremotoResponse = serviceApi.getTerremotos()
            //Log.d("API",terremotosString)
            val terremotosList = parserTerremotosJson(terremotos)
            //mutableListOf<Terremoto>()
            terremotosList
        }
    }

    private fun parserTerremotosJson(terremotosJsonResponse: TerremotoResponse): MutableList<Terremoto> {

        val terremotosList = mutableListOf<Terremoto>()
        val featuresList = terremotosJsonResponse.features

        for (feature in featuresList) {
            val properties: Properties = feature.properties

            val id = feature.id

            val magnitude = properties.magnitude
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            terremotosList.add(Terremoto(id, place, magnitude, time, longitude, latitude))

        }

        return terremotosList

    }

}
