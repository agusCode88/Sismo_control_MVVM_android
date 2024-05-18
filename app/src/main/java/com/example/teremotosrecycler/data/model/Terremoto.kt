package com.example.teremotosrecycler.data.model

data class Terremoto(
    val id: String,
    val lugar:String,
    val magnitud:Double = 0.0,
    val time: Long = 0,
    val longitud: Double = 0.0,
    val latitud:Double = 0.0) {

    companion object {

        val dataEmpty= mutableListOf<Terremoto>()

    }
}