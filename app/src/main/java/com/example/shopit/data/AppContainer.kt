package com.example.shopit.data

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


interface AppContainer{
    val databaseRepository: DatabaseRepository
}

class DefaultAppContainer : AppContainer{
    private val database = Firebase.database
    override val databaseRepository: DatabaseRepository by lazy {
        DefaultDatabaseRepository(database = database)
    }
}
