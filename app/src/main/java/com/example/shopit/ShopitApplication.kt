package com.example.shopit

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.shopit.data.worker.MyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ShopitApplication: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: MyWorkerFactory
    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, workManagerConfiguration)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}