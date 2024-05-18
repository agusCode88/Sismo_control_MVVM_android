package com.example.teremotosrecycler.data.repository

import com.example.teremotosrecycler.data.model.Terremoto

interface TerremotoRepository {
    suspend fun fetchTerremotos(): MutableList<Terremoto>
}