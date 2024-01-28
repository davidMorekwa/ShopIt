package com.example.shopit.data.worker//package com.example.shopit.data.worker
//
//import android.content.Context
//import androidx.work.Constraints
//import androidx.work.NetworkType
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//
//interface WorkerRepository {
//    fun syncData()
//}
//
//class DefaultWorkerRepository(context: Context): WorkerRepository{
//    private var workMgr =  WorkManager.getInstance(context)
//    override fun syncData() {
//        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true)
//            .setRequiresStorageNotLow(true)
//            .setRequiredNetworkType(NetworkType.CONNECTED)
////        val syncDataWorker = PeriodicWorkRequestBuilder<DataSyncWorker>(1, TimeUnit.MINUTES)
//            val syncDataWorker = OneTimeWorkRequestBuilder<DataSyncWorker>()
//            .setConstraints(constraints.build())
//
//
////        workMgr.enqueueUniqueWork("Sync Data Periodic Worker", ExistingWorkPolicy.KEEP, syncDataWorker.build())
//
//    }
//}