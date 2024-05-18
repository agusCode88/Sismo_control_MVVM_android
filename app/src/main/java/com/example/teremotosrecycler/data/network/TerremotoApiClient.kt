package com.example.teremotosrecycler.data.network

import com.example.teremotosrecycler.data.model.response.TerremotoResponse
import retrofit2.http.GET

interface TerremotoApiClient {

    @GET("all_hour.geojson")
    suspend fun getLastHoursEq(): TerremotoResponse
}