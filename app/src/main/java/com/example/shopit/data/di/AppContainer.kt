package com.example.shopit.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.shopit.auth.custom.AuthRepository
import com.example.shopit.auth.custom.AuthRepositoryImpl
import com.example.shopit.data.local.DefaultLocalDatabaseRepository
import com.example.shopit.data.local.LocalDatabaseRepository
import com.example.shopit.data.local.ShopitDatabase
import com.example.shopit.data.model.CategoryEntity
import com.example.shopit.data.model.ProductEntity
import com.example.shopit.data.remote.darajaApi.ApiServiceRepository
import com.example.shopit.data.remote.darajaApi.DarajaApiService
import com.example.shopit.data.remote.darajaApi.DefaultApiServiceRepository
import com.example.shopit.data.remote.repository.DefaultDatabaseRepository
import com.example.shopit.data.remote.repository.FirebaseRemoteMediator
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository
import com.example.shopit.data.worker.DefaultWorkerRepository
import com.example.shopit.data.worker.WorkerRepository
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
    val workerRepository: WorkerRepository
//    val firebasePager: Pager<String, Product>
    val roomPager: Pager<Int, ProductEntity>
    val categoryPager: Pager<Int, CategoryEntity>
}
private const val TOKEN_PREFERENCE_NAME = "token_preference"

class DefaultAppContainer(
    context: Context,
) : AppContainer {
    private val database = Firebase.database
    private val BASE_URL = "https://sandbox.safaricom.co.ke"
    private val auth = FirebaseAuth.getInstance()
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val shopitDatabase: ShopitDatabase = ShopitDatabase.getDatabase(context)

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
        DefaultLocalDatabaseRepository(shopitDatabase.productsDao())
    }
    override val workerRepository: WorkerRepository = DefaultWorkerRepository(context)
//    @OptIn(ExperimentalPagingApi::class)
//    override val firebasePager: Pager<String, Product>
//        get() = Pager(
//            config = PagingConfig(pageSize = 30),
//            pagingSourceFactory = {
//                FirebasePagingSource(
//                    databaseReference = database.getReference("Products"),
//                    remoteDatabaseRepository = remoteDatabaseRepository,
//                    shopitDatabase = shopitData base
//                ) },
//        )
    @OptIn(ExperimentalPagingApi::class)
    override val roomPager: Pager<Int, ProductEntity>
        get() = Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 60),
            pagingSourceFactory = {localDatabaseRepository.getAllProducts()},
            remoteMediator = FirebaseRemoteMediator(shopitDatabase, remoteDatabaseRepository, localDatabaseRepository)
        )

    override val categoryPager: Pager<Int, CategoryEntity>
        get() = Pager(
            config = PagingConfig(5, initialLoadSize = 10),
            pagingSourceFactory = {localDatabaseRepository.getCategories()}
        )
}
