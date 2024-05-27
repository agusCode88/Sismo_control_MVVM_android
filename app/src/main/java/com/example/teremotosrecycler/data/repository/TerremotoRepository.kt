package com.example.teremotosrecycler.data.repository

import com.example.teremotosrecycler.data.model.Terremoto

interface TerremotoRepository {
    suspend fun fetchTerremotos( orderByMagnitude:Boolean): MutableList<Terremoto>
    suspend fun getTerremotosDB(): MutableList<Terremoto>
    suspend fun saveTerremotosDB(terremotos: MutableList<Terremoto>)
    suspend fun getOrderByMagnitudeDB(): MutableList<Terremoto>



}