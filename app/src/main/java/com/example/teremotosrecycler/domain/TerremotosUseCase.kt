package com.example.teremotosrecycler.domain

import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.repository.TerremotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TerremotosUseCase(private val repository: TerremotoRepository) {
    suspend fun gelAllEartquakes(): MutableList<Terremoto> {
        return repository.fetchTerremotos()
    }

    suspend fun getTerremotosFromDatabase(): MutableList<Terremoto>{
        return withContext(Dispatchers.IO){
              repository.getTerremotosDB()
        }
    }

    suspend fun saveTerremotos(terremotos: MutableList<Terremoto>) {
        repository.saveTerremotosDB(terremotos)
    }

}