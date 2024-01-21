package com.example.shopit.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

interface WorkerRepository {
    fun syncData()
}

class DefaultWorkerRepository(context: Context): WorkerRepository{
    private val workMgr = WorkManager.getInstance(context)
    override fun syncData() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
        val syncDataWorker = PeriodicWorkRequestBuilder<DataSyncWorker>(1, TimeUnit.MINUTES)
            .setConstraints(constraints.build())


        workMgr.enqueueUniquePeriodicWork("Sync Data Periodic Worker", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, syncDataWorker.build())

    }
}