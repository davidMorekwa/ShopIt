package com.example.shopit.data

import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


interface AppContainer{
    val remoteDatabaseRepository: RemoteDatabaseRepository
}

class DefaultAppContainer : AppContainer{
    private val database = Firebase.database
    override val remoteDatabaseRepository: RemoteDatabaseRepository by lazy {
        DefaultDatabaseRepository(database = database)
    }
}
