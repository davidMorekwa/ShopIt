package com.example.shopit.data.network

import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.callback.DarajaResult
import com.androidstudy.daraja.util.Environment
import com.example.shopit.data.model.OAuthResponse
import com.example.shopit.data.utlis.AppUtils
import com.example.shopit.data.utlis.Config
import com.google.gson.Gson
import retrofit2.Response


interface ApiServiceRepository {
    suspend fun getOAuthAccessoken(): Response<OAuthResponse>
    suspend fun makePayment(phoneNumber: String, amount: String, token: String)
}

class DefaultApiServiceRepository(private val apiService: DarajaApiService):ApiServiceRepository{
    val gson = Gson()
    override suspend fun getOAuthAccessoken(): Response<OAuthResponse> {
        val response = apiService.getOAuthToken()
        println("RESPONSE!!")
        println(response.body()?.accessToken)
        return response
    }

    override suspend fun makePayment(phoneNumber: String, amount: String, token: String) {
        val daraja = getDaraja()
        val response = daraja.initiatePayment(
            token ="dHtKoGLgfFpRFpVvYA0WteefVQNL",
            phoneNumber = phoneNumber,
            amount = "1",
            accountReference = AppUtils.generateUUID(),
            description = "Purchase of Goods on ShopIt"
        ) { darajaResult ->
            when (darajaResult) {
                is DarajaResult.Success -> {
                    val result = darajaResult.value
                    println("RESULT: ${result}")
                }
                is DarajaResult.Failure -> {
                    val exception = darajaResult.darajaException
                    if (darajaResult.isNetworkError) {
                        println("Network Error")
                    } else {
                        println("Payment Failure")
                        println(exception.toString())
                    }
                }
            }
        }
        return response
    }
    private fun getDaraja(): Daraja {
        return Daraja.builder(Config.CONSUMER_KEY, Config.CONSUMER_SECRET)
            .setBusinessShortCode(Config.BUSINESS_SHORTCODE)
            .setPassKey(AppUtils.passKey)
            .setTransactionType(Config.ACCOUNT_TYPE)
            .setCallbackUrl(Config.CALLBACK_URL)
            .setEnvironment(Environment.SANDBOX)
            .build()
    }

}