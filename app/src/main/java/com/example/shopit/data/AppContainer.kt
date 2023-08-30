package com.example.shopit.data

import com.example.shopit.data.network.ApiServiceRepository
import com.example.shopit.data.network.DarajaApiService
import com.example.shopit.data.network.DefaultApiServiceRepository
import com.example.shopit.data.remote.DefaultDatabaseRepository
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


interface AppContainer{
    val remoteDatabaseRepository: RemoteDatabaseRepository
    val apiServiceRepository: ApiServiceRepository
}

class DefaultAppContainer : AppContainer{
    private val database = Firebase.database
    private val BASE_URL = "https://sandbox.safaricom.co.ke/"
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val retrofitService: DarajaApiService by lazy {
        retrofit.create(DarajaApiService::class.java)
    }
    override val remoteDatabaseRepository: RemoteDatabaseRepository by lazy {
        DefaultDatabaseRepository(database = database)
    }
    override val apiServiceRepository: ApiServiceRepository by lazy {
        DefaultApiServiceRepository(retrofitService)
    }


}
