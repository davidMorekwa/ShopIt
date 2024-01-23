package com.example.shopit.ui.screens.homescreen

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.network.darajaApi.PreferenceKeys
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val dataStore: DataStore<Preferences>,
):ViewModel() {
    private var _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    var homeUiState = _homeUiState.asStateFlow()
    private var _categoryList: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val categoryList = _categoryList.asStateFlow()
    private var _toggleSwitchState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val toggleSwitchState = _toggleSwitchState.asStateFlow()

    companion object{
        const val TIMEOUT_MILLS = 2000L
    }
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
                var products = remoteDatabaseRepository.getInitialProducts()
                println("PRODUCTS ${products.size}")
//                storeLocally(products)
                _homeUiState.value = HomeUiState.Success(products)

            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                _homeUiState.value = HomeUiState.Error
            }
            _categoryList.value = getCategories(_homeUiState.value)
        }
    }
    fun filterProductsByCategory(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(category == "All"){
                getInitialProducts()
            }
            _homeUiState.value = try{
                var products = remoteDatabaseRepository.filterProductsByCategory(category)
                println("PRODUCTS IN CATEGORY $category: ${products.size}")
                HomeUiState.Success(products)
            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                HomeUiState.Error
            }
        }
    }
    fun networkUnavailable(){
        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Error
        }
    }
    fun showOfflineData(){
        viewModelScope.launch(Dispatchers.IO) {
            println("Network unavailable")
            try {
                var state = localDatabaseRepository.getAllProducts()
                var productList: MutableList<Product> = mutableListOf()
                state.collect{ productEntityList
                    ->
                    for (it in productEntityList){
                        productList.add(it.toDomainProduct())
                    }
                }
//                _homeUiState.value = HomeUiState.Success(productList)
                print("Products from local database: ${productList.size}")
            } catch (e: Exception){
                Log.e("HOMESCREENVIEWMODEL NETWORK UNAVAILABLE ERROR", e.message!!)
                _homeUiState.value = HomeUiState.Error
            }
        }
    }
}

