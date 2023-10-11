package com.example.shopit.data

import com.example.shopit.auth.custom.AuthRepository
import com.example.shopit.auth.custom.AuthRepositoryImpl
import com.example.shopit.data.network.ApiServiceRepository
import com.example.shopit.data.network.DarajaApiService
import com.example.shopit.data.network.DefaultApiServiceRepository
import com.example.shopit.data.remote.DefaultDatabaseRepository
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


interface AppContainer{
    val remoteDatabaseRepository: RemoteDatabaseRepository
    val apiServiceRepository: ApiServiceRepository
    val authRepository: AuthRepository
}

class DefaultAppContainer : AppContainer{
    private val database = Firebase.database
    private val BASE_URL = "https://sandbox.safaricom.co.ke"
    private val auth = FirebaseAuth.getInstance()
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val client = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor("kFmHYxzmg8U6ojqZrK06AyVRfL2a"))
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
    override val apiServiceRepository: ApiServiceRepository by lazy {
        DefaultApiServiceRepository(retrofitService)
    }
    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(auth, database)
    }
}
class HeaderInterceptor(_token: String) : Interceptor{
    private val token = _token
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(modifiedRequest)
    }

}
/*
K3nrr08PbmplVoY+npKTIYI8rjmNFhmPXVPiXyWb5IK1N/z/6PV6z8YfUj5kXJSXXiiOh5aBZM9+6vY8AdZ87k8rTqFyhZehfpNNbwrpi/6+ZhHZGWdbR1WpoqQPTXtUGBVb7uXvReV0Q38uXqWSLl3gYYto0ZBbgohnSU0pymuJDZJEtItA0nXajBROhWm0T/FDL8lLuOYAQqX6qY3T5/8hkPf4xFCO41Hz0rFGwYg5fVRf0OvKtLmtkWw7nkS4U7jq14g/ns1tbPEqVUlJQazlMIo7yL7kGCoXpnQgmsMT5/Y5zl6RkCn72eqyswlNCqf005uZfJuAnDjQogDSNw==
 */
