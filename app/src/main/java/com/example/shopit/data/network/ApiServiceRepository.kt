package com.example.shopit.data.network

import com.example.shopit.data.model.LipaNaMpesaResponse
import com.example.shopit.data.model.OAuthResponse

interface ApiServiceRepository {
    suspend fun getOAuthAccessoken():OAuthResponse
    suspend fun makePayment():LipaNaMpesaResponse
}

class DefaultApiServiceRepository(private val apiService: DarajaApiService):ApiServiceRepository{
    override suspend fun getOAuthAccessoken(): OAuthResponse {
        val accessTokenResponse: OAuthResponse = apiService.getOAuthAccessToken()
        return accessTokenResponse
    }

    override suspend fun makePayment(): LipaNaMpesaResponse {
        TODO()
    }

}