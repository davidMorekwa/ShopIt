package com.example.shopit.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.shopit.auth.custom.AuthRepository
import com.example.shopit.auth.custom.AuthRepositoryImpl
import com.example.shopit.data.local.DefaultLocalDatabaseRepository
import com.example.shopit.data.local.LocalDatabaseRepository
import com.example.shopit.data.local.ShopitDatabase
import com.example.shopit.data.remote.darajaApi.ApiServiceRepository
import com.example.shopit.data.remote.darajaApi.DarajaApiService
import com.example.shopit.data.remote.darajaApi.DefaultApiServiceRepository
import com.example.shopit.data.remote.repository.DefaultDatabaseRepository
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


interface AppContainer{
    val remoteDatabaseRepository: RemoteDatabaseRepository
    val apiServiceRepository: ApiServiceRepository
    val authRepository: AuthRepository
    val dataStoreInstance: DataStore<Preferences>
    val localDatabaseRepository: LocalDatabaseRepository
}
private const val TOKEN_PREFERENCE_NAME = "token_preference"

class DefaultAppContainer(
    context: Context,
) : AppContainer{
    private val database = Firebase.database
    private val BASE_URL = "https://sandbox.safaricom.co.ke"
    private val auth = FirebaseAuth.getInstance()
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
//        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val retrofitService: DarajaApiService by lazy {
        retrofit.create(DarajaApiService::class.java)
    }
    override val remoteDatabaseRepository: RemoteDatabaseRepository by lazy {
        DefaultDatabaseRepository(database = database)
    }
    private val Context.dataStore by preferencesDataStore(
        name = TOKEN_PREFERENCE_NAME
    )
    override val apiServiceRepository: ApiServiceRepository by lazy {
        DefaultApiServiceRepository(retrofitService, context.dataStore)
    }
    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(auth, database)
    }
    override val dataStoreInstance: DataStore<Preferences> by lazy {
        context.dataStore
    }
    override val localDatabaseRepository: LocalDatabaseRepository by lazy {
        DefaultLocalDatabaseRepository(ShopitDatabase.getDatabase(context = context).productsDao())
    }
}
