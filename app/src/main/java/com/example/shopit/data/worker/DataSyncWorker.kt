package com.example.shopit.data.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shopit.data.local.LocalDatabaseRepository
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository

class DataSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val remoteRepository: RemoteDatabaseRepository,
    private val database: LocalDatabaseRepository
): CoroutineWorker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        showSyncNotification(applicationContext, "Syncing has started")
//        val productList = remoteRepository.getInitialProducts()
//        database.upsertProducts(productList)

        return Result.success()

    }
}