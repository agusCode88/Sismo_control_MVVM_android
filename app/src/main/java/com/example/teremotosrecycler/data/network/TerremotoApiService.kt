package com.example.teremotosrecycler.data.network

import com.example.teremotosrecycler.data.model.response.TerremotoResponse
import com.example.teremotosrecycler.domain.RetrofitHelper

class TerremotoApiService {

    private var serviceRetrofit = RetrofitHelper.getRetrofit()

    suspend fun getTerremotos(): TerremotoResponse {
        val response: TerremotoResponse = serviceRetrofit.create(TerremotoApiClient::class.java).getLastHoursEq()
        return response
    }
}