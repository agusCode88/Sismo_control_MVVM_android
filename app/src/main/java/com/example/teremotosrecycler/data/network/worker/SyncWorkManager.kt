package com.example.teremotosrecycler.data.network.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.teremotosrecycler.data.local.TerremotoDatabase
import com.example.teremotosrecycler.data.repository.MainRepository

class SyncWorkManager(appContext: Context , parent:WorkerParameters): CoroutineWorker(appContext,parent) {
    companion object{
        const val WORK_NAME = "SynWorkManager"
    }

    private val dataBase = TerremotoDatabase.getDatabase(appContext)
    private val repository = MainRepository(dataBase.terremotoDao())

    override suspend fun doWork(): Result {
        repository.fetchTerremotos(false)
        return Result.success()
    }
}