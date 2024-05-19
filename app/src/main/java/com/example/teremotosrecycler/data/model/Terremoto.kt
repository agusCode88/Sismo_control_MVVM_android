package com.example.teremotosrecycler.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terremotos")
data class Terremoto(
    @PrimaryKey
    val id: String,
    val lugar:String,
    val magnitud:Double = 0.0,
    val time: Long = 0,
    val longitud: Double = 0.0,
    val latitud:Double = 0.0) {

}