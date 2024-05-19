package com.example.teremotosrecycler.data.repository

import androidx.lifecycle.LiveData
import com.example.teremotosrecycler.data.local.TerremotoDAO
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.model.response.Properties
import com.example.teremotosrecycler.data.model.response.TerremotoResponse
import com.example.teremotosrecycler.data.network.TerremotoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val terremotoDao: TerremotoDAO): TerremotoRepository {

    private var serviceApi = TerremotoApiService()

    // Cuando tenemos una corrutina en un método , hay que utilizar el suspend
    override suspend fun fetchTerremotos(): MutableList<Terremoto> {

        return withContext(Dispatchers.IO){
            val terremotos: TerremotoResponse = serviceApi.getTerremotos()
            //Log.d("API",terremotosString)
            val terremotosList = parserTerremotosJson(terremotos)

            saveTerremotosDB(terremotosList)
            //mutableListOf<Terremoto>()
            terremotosList
        }
    }

    override suspend fun getTerremotosDB(): MutableList<Terremoto> {
        return terremotoDao.getAlTerremotos()
    }

    override suspend fun saveTerremotosDB(terremotos: MutableList<Terremoto>) {
        withContext(Dispatchers.IO) {
            terremotoDao.insertAll(terremotos)
        }
    }

    private fun parserTerremotosJson(terremotosJsonResponse: TerremotoResponse): MutableList<Terremoto>{

        val terremotosList = mutableListOf<Terremoto>()
        val featuresList = terremotosJsonResponse.features

        for(feature in featuresList){
            val properties: Properties = feature.properties

            val id = feature.id

            val magnitude = properties.magnitude
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            terremotosList.add(Terremoto(id,place,magnitude,time,longitude,latitude))

        }

        return terremotosList

    }
}