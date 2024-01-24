package com.example.shopit.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

interface WorkerRepository {
    fun syncData()
}

class DefaultWorkerRepository(context: Context): WorkerRepository{
    private var workMgr =  WorkManager.getInstance(context)
    override fun syncData() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
//        val syncDataWorker = PeriodicWorkRequestBuilder<ProductTableSyncWorker>(15, TimeUnit.MINUTES)
            val syncDataWorker = OneTimeWorkRequestBuilder<ProductTableSyncWorker>()
            .setConstraints(constraints)
            .build()


//       workMgr.enqueueUniqueWork("Sync Data Periodic Worker", ExistingPeriodicWorkPolicy.KEEP, syncDataWorker)
        workMgr.enqueueUniqueWork("Sync Data Periodic Worker", ExistingWorkPolicy.KEEP, syncDataWorker)
//        workMgr.enqueueUniquePeriodicWork("Sync Data Periodic Worker", ExistingPeriodicWorkPolicy.KEEP, syncDataWorker)
    }
}