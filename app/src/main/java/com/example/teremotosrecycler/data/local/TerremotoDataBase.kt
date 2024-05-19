package com.example.teremotosrecycler.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teremotosrecycler.data.model.Terremoto

@Database(entities = [Terremoto::class], version = 1, exportSchema = false)
abstract class TerremotoDatabase : RoomDatabase() {
    abstract fun terremotoDao(): TerremotoDAO

    companion object {
        @Volatile
        private var INSTANCE: TerremotoDatabase? = null

        fun getDatabase(context: Context): TerremotoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TerremotoDatabase::class.java,
                    "terremoto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}