package com.example.shopit.data.worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductTableSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val remoteRepository: RemoteDatabaseRepository,
    private val database: LocalDatabaseRepository
): CoroutineWorker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        showSyncNotification(applicationContext, "Sync has started")
        return withContext(Dispatchers.Default){
            return@withContext try{
                Log.d("SYNC WORKER", "The background worker has started")
                val productList = remoteRepository.getInitialProducts()
                val result = database.insertProducts(productList)
                if (!result.isNullOrEmpty()) {
                    Log.d("SYNC WORKER", "The background worker has finished successfully")
                    showSyncNotification(applicationContext, "Sync has finished")
                    Result.success()
                } else{
                    Log.e("SYNC WORKER", "The background worker has an error")
                    Result.failure()
                }
            } catch (e: Exception){
                Log.e("DATA SYNC WORKER EXCEPTION", e.message!!)
                Result.failure()
            }
        }


    }
}