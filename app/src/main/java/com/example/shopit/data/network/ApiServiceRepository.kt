package com.example.shopit.data.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.callback.DarajaResult
import com.androidstudy.daraja.util.Environment
import com.example.shopit.data.utlis.AppUtils
import com.example.shopit.data.utlis.Config
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


interface ApiServiceRepository {
    suspend fun getOAuthAccessoken(): String?
    suspend fun makePayment(phoneNumber: String, amount: String, token: String)
}

object PreferenceKeys{
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val EXPIRY = stringPreferencesKey("expiry")
    val USE_DARK_THEME = booleanPreferencesKey("use_dark_theme")
}

class DefaultApiServiceRepository(
    private val apiService: DarajaApiService,
    private val dataStore: DataStore<Preferences>
):ApiServiceRepository{
    override suspend fun getOAuthAccessoken(): String? {
        var currentTime = System.currentTimeMillis()/1000
        var token_expiry = dataStore.data
            .map { value: Preferences ->
                value[PreferenceKeys.EXPIRY]
            }
            .first()
        var token: String? = null
        if(currentTime > token_expiry?.toLong() ?: 0){
            println("Token has expired")
            val response = apiService.getOAuthToken()
            token = response.body()?.accessToken
            val expiresIn = response.body()?.expiresIn
            var currentTime2 = System.currentTimeMillis()/1000
            var expiryTime = currentTime2.toInt() + (expiresIn?.toInt() ?: 0)
            dataStore.edit { mutablePreferences: MutablePreferences ->
                token?.let { mutablePreferences[PreferenceKeys.ACCESS_TOKEN] = it }
                mutablePreferences[PreferenceKeys.EXPIRY] = expiryTime.toString()
            }
        } else {
            token = dataStore.data
                .map { value: Preferences ->
                    value[PreferenceKeys.ACCESS_TOKEN]
                }
                .first()
        }

        return token
    }

    override suspend fun makePayment(phoneNumber: String, amount: String, token: String) {
        val daraja = getDaraja()
        println("MAKE PAYMENT TOKEN: $token")
        val response = daraja.initiatePayment(
            token = token,
            phoneNumber = phoneNumber,
            amount = amount,
            accountReference = "ShopIt App",
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