package com.example.shopit.ui.screens.settingsscreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class SettingsScreenViewModel (private val dataStore: DataStore<Preferences>): ViewModel(){
    var isDarkTheme:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _isDarkTheme = isDarkTheme.asStateFlow()

}