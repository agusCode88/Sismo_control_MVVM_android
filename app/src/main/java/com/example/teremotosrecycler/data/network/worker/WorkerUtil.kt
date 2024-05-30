package com.example.teremotosrecycler.data.network.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkerUtil {
    fun scheduleSync(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val synRequest = PeriodicWorkRequestBuilder<SyncWorkManager>(1 ,TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                SyncWorkManager.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                synRequest
            )
    }
}