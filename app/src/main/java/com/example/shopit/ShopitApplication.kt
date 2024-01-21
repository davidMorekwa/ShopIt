package com.example.shopit

import android.app.Application
import com.example.shopit.data.AppContainer
import com.example.shopit.data.DefaultAppContainer

class ShopitApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
//        val config = Configuration.Builder()
//            .setWorkerFactory(WorkerFactory(repository = container.remoteDatabaseRepository, database = container.localDatabaseRepository))
//            .build()
//
//        WorkManager.initialize(this, config)
    }
}