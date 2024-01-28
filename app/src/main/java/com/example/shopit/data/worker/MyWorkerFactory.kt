//package com.example.shopit.data.worker
//
//import android.content.Context
//import androidx.work.ListenableWorker
//import androidx.work.WorkerFactory
//import androidx.work.WorkerParameters
//import com.example.shopit.data.repositories.local.LocalDatabaseRepository
//import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
//import javax.inject.Inject
//
//class MyWorkerFactory @Inject constructor(
//    private val remoteDatabaseRepository: RemoteDatabaseRepository,
//    private val localDatabaseRepository: LocalDatabaseRepository
//): WorkerFactory() {
//    override fun createWorker(
//        appContext: Context,
//        workerClassName: String,
//        workerParameters: WorkerParameters
//    ): ListenableWorker? {
//        return when(workerClassName){
//            ProductTableSyncWorker::class.java.name ->{
//                ProductTableSyncWorker(context = appContext, workerParams = workerParameters, remoteRepository = remoteDatabaseRepository, database = localDatabaseRepository )
//            }
//
//            else -> {null}
//        }
//    }
//}