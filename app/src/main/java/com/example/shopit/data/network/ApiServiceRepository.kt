package com.example.shopit.data.network

import com.example.shopit.data.model.LipaNaMpesaRequest
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response


interface ApiServiceRepository {
    suspend fun getOAuthAccessoken(): Response
    suspend fun makePayment(request: LipaNaMpesaRequest, token: String): Response
}

class DefaultApiServiceRepository(private val apiService: DarajaApiService):ApiServiceRepository{
    val gson = Gson()
    override suspend fun getOAuthAccessoken(): Response {
        val client = OkHttpClient().newBuilder().build()
        val request: Request = Request.Builder()
            .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
            .method("GET", null)
            .addHeader(
                "Authorization",
                "Basic cFJZcjZ6anEwaThMMXp6d1FETUxwWkIzeVBDa2hNc2M6UmYyMkJmWm9nMHFRR2xWOQ=="
            )
            .build()
        val response: Response = client.newCall(request).execute()
        return response
    }

    override suspend fun makePayment(request: LipaNaMpesaRequest, token: String): Response {
//        println("DefautApiRepo Access Token: $token")
//        val response = apiService.makePayment(request)
//        return response
        val client = OkHttpClient().newBuilder().build();
        val mediaType = "application/json".toMediaTypeOrNull();

        val reque = gson.toJson(request)

        val body = RequestBody.create(
            mediaType,
            reque
        )
        val req = Request.Builder()
            .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build();
        println("Request: $req")
        val response = client.newCall(req).execute()
        return response
    }

}