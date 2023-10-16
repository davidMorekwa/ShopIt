package com.example.shopit

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.shopit.data.AppContainer
import com.example.shopit.data.DefaultAppContainer
private const val TOKEN_PREFERENCE_NAME = "token_preference"
class ShopitApplication:Application() {
    lateinit var container: AppContainer
    val Context.dataStore by preferencesDataStore(
        name = TOKEN_PREFERENCE_NAME
    )
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(dataStore)
    }
}