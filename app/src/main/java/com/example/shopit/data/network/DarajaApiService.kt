package com.example.shopit.data.network

import com.example.shopit.data.model.LipaNaMpesaRequest
import com.example.shopit.data.model.LipaNaMpesaResponse
import com.example.shopit.data.model.OAuthResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface DarajaApiService {
    @Headers("Authorization:Basic MGR0TmFnR0JHR0hLMGhDMFRPWDVUQko3UnYySEsxbGk6TTJURWh3cnplTnB2RzdjZA")
    @GET("/oauth/v1/generate?grant_type=client_credentials")
    suspend fun getOAuthAccessToken(): OAuthResponse
    @POST("stkpush/v1/processrequest")
    suspend fun makePayment(request: LipaNaMpesaRequest) : LipaNaMpesaResponse
}