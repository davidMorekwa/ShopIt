package com.example.shopit.fake

import com.example.shopit.data.model.LipaNaMpesaRequest
import com.example.shopit.data.model.LipaNaMpesaResponse
import com.example.shopit.data.model.OAuthResponse
import com.example.shopit.data.network.ApiServiceRepository
import okhttp3.Response

class FakeDefaultApiServiceRepository(): ApiServiceRepository {
    override suspend fun getOAuthAccessoken(): OAuthResponse {
        return FakeDataSource.response
    }

    override suspend fun makePayment(request: LipaNaMpesaRequest): LipaNaMpesaResponse {
        return LipaNaMpesaResponse(
                merchantRequestID= "114121-152704411-1",
                checkoutRequestID= "ws_CO_02092023152749161708374149",
                responseCode= "0",
                responseDescription= "Success. Request accepted for processing",
                customerMessage = "Success. Request accepted for processing"
        )
    }
}