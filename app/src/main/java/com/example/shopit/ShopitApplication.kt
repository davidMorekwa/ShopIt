package com.example.shopit

import android.app.Application
import com.example.shopit.data.di.AppContainer
import com.example.shopit.data.di.DefaultAppContainer
class ShopitApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
//        WorkManager.initialize(this, workManagerConfiguration)
    }

//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(WorkerFactory(repository = container.remoteDatabaseRepository, database = container.localDatabaseRepository))
//            .setMinimumLoggingLevel(android.util.Log.INFO)
//            .build()
//    }
}