package com.example.shopit.data.network

import com.example.shopit.data.model.OAuthResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface DarajaApiService {
    @GET("/oauth/v1/generate?grant_type=client_credentials")
    @Headers("Authorization: Basic cFJZcjZ6anEwaThMMXp6d1FETUxwWkIzeVBDa2hNc2M6UmYyMkJmWm9nMHFRR2xWOQ==")
    suspend fun getOAuthToken(): Response<OAuthResponse>
}