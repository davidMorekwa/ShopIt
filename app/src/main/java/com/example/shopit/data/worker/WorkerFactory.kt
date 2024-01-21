package com.example.shopit.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.shopit.data.local.LocalDatabaseRepository
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository

class WorkerFactory(
    private val repository: RemoteDatabaseRepository,
    private val database: LocalDatabaseRepository
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName){
            DataSyncWorker::class.java.name ->{
                DataSyncWorker(context = appContext, workerParams = workerParameters, remoteRepository = repository, database = database )
            }

            else -> {null}
        }
    }
}