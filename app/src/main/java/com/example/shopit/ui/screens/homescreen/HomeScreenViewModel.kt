package com.example.shopit.ui.screens.homescreen

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.local.LocalDatabaseRepository
import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.darajaApi.PreferenceKeys
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException


class HomeScreenViewModel(
    private val repository: RemoteDatabaseRepository,
    private val dataStore: DataStore<Preferences>,
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val context: Context
):ViewModel() {
    private var _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        private set
    var homeUiState = _homeUiState.asStateFlow()
    private var _categoryList: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val categoryList = _categoryList.asStateFlow()
    private var _toggleSwitchState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val toggleSwitchState = _toggleSwitchState.asStateFlow()

    init {
        getTheme()
        println("Getting products")
    }

    private fun getTheme() {
        viewModelScope.launch(Dispatchers.Main) {
            _toggleSwitchState.value = dataStore.data
                .map { value: Preferences -> value[PreferenceKeys.USE_DARK_THEME] ?: false }
                .first()
        }
    }
    fun changeTheme(status: Boolean){
        viewModelScope.launch(Dispatchers.Main) {
            _toggleSwitchState.value = status
            dataStore.edit { mutablePreferences: MutablePreferences ->
                mutablePreferences[PreferenceKeys.USE_DARK_THEME] = status
            }
        }

    }
    fun getCategories(homeUiState: HomeUiState): List<String>{
        var tempCategoryList: MutableList<String> = mutableListOf("All")
        if (homeUiState is HomeUiState.Success){
            for (product in (_homeUiState.value as HomeUiState.Success).products){
                if(!tempCategoryList.contains(product.primary_category.toString())){
                    tempCategoryList.add(product.primary_category.toString())
                }
            }
        } else {
            return emptyList()
        }
        println("categories size: ${tempCategoryList.size}")
        return tempCategoryList.toList()
    }
    fun getInitialProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                var products = repository.getInitialProducts()
                println("PRODUCTS ${products.size}")
//                storeLocally(products)
                _homeUiState.value = HomeUiState.Success(products.shuffled())

            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                _homeUiState.value = HomeUiState.Error
            }
            _categoryList.value = getCategories(_homeUiState.value)
        }
    }
    fun storeLocally(productList: List<Product>){
        viewModelScope.launch {
            localDatabaseRepository.upsertProducts(productList)
        }
    }
    fun filterProductsByCategory(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(category == "All"){
                getInitialProducts()
            }
            _homeUiState.value = try{
                var products = repository.filterProductsByCategory(category)
                println("PRODUCTS IN CATEGORY $category: ${products.size}")
                HomeUiState.Success(products)
            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                HomeUiState.Error
            }
        }
    }
    fun networkUnavailable(){
        _homeUiState.value = HomeUiState.Error
    }
}

