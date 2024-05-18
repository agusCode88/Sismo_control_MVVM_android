package com.example.teremotosrecycler.domain

import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.repository.TerremotoRepository

class TerremotosUseCase(private val repository: TerremotoRepository) {
    suspend fun gelAllEartquakes(): MutableList<Terremoto> {
        return repository.fetchTerremotos()
    }
}