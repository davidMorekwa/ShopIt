package com.example.shopit.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.shopit.auth.custom.AuthRepository
import com.example.shopit.auth.custom.AuthRepositoryImpl
import com.example.shopit.data.network.darajaApi.ApiServiceRepository
import com.example.shopit.data.network.darajaApi.DarajaApiService
import com.example.shopit.data.network.darajaApi.DefaultApiServiceRepository
import com.example.shopit.data.repositories.local.DefaultLocalDatabaseRepository
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.local.ShopitDatabase
import com.example.shopit.data.repositories.remote.DefaultRemoteDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import com.example.shopit.data.worker.DefaultWorkerRepository
import com.example.shopit.data.worker.MyWorkerFactory
import com.example.shopit.data.worker.WorkerRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase() = Firebase.database

    @Provides
    @Singleton
    fun providesAuth() = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideRemoteDatabaseRepository(database: FirebaseDatabase): RemoteDatabaseRepository =
        DefaultRemoteDatabaseRepository(database)

    @Singleton
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth, database: FirebaseDatabase): AuthRepository =
        AuthRepositoryImpl(auth, database)

    @Singleton
    @Provides
    fun provideApiServiceRepository(
        service: DarajaApiService,
        dataStore: DataStore<Preferences>
    ): ApiServiceRepository =
        DefaultApiServiceRepository(service, dataStore)

    @Singleton
    @Provides
    fun provideLocalDatabaseRepository(@ApplicationContext context: Context): LocalDatabaseRepository {
        val dao = ShopitDatabase.getDatabase(context).productsDao()
        return DefaultLocalDatabaseRepository(dao)
    }

    private const val BASE_URL = "https://sandbox.safaricom.co.ke"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun provideDarajaApiService(retrofit: Retrofit): DarajaApiService = retrofit.create(
        DarajaApiService::class.java)

    private const val TOKEN_PREFERENCE_NAME = "token_preference"
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_PREFERENCE_NAME)
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Singleton
    @Provides
    fun provideWorkerRepository(@ApplicationContext context: Context): WorkerRepository = DefaultWorkerRepository(context)

    @Singleton
    @Provides
    fun provideMyWorkerFactory(remoteDatabaseRepository: RemoteDatabaseRepository, localDatabaseRepository: LocalDatabaseRepository): MyWorkerFactory = MyWorkerFactory(remoteDatabaseRepository, localDatabaseRepository)
}