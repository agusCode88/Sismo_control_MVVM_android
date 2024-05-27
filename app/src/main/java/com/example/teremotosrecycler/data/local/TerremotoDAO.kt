package com.example.teremotosrecycler.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.teremotosrecycler.data.model.Terremoto

@Dao
interface TerremotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( terremotos: MutableList<Terremoto> )
    @Query("SELECT * FROM terremotos")
    suspend fun getAlTerremotos(): MutableList<Terremoto>
    @Delete
    fun delete(vararg  terremoto: Terremoto)

    @Update
    fun update(vararg  terremoto: Terremoto)

   /* @Query("SELECT * FROM terremotos WHERE magnitud > :magnitude")
    fun getTerremotoByMagnitude(magnitude: Double):MutableList<Terremoto>*/

    @Query("SELECT * FROM terremotos order by  magnitud asc")
    fun getTerremotoByMagnitude():MutableList<Terremoto>

}