package com.example.shopit.ui.screens.homescreen

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.network.connectionObserver.ConnectivityObserver
import com.example.shopit.data.network.darajaApi.PreferenceKeys
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.local.ProductEntity
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/*
TODO: Create a DataRepository class that will get data from the local and remote repositories
 */

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val dataStore: DataStore<Preferences>,
    private val connectivityObserver: ConnectivityObserver
):ViewModel() {
    var networkStatus: StateFlow<NetworkState> = connectivityObserver.observe().map {
            it -> NetworkState(it)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
            NetworkState(status = ConnectivityObserver.Status.Unavailable)
        )
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
            _homeUiState.value = HomeUiState.Loading
            println("On retry click")
            Log.i("SHOW OFFLINE DATA LOG", "started")
            try {
//                val products = localDatabaseRepository.getAllProducts()
                localDatabaseRepository.getAllProducts()
                    .map {
                        println("Size: ${it.size}")
                        if (!it.isNullOrEmpty()){
                            HomeUiState.Success(it.toProductList())
                        } else {
                            HomeUiState.Error
                        }
                    }
                    .collect(){ newState ->
                        _homeUiState.value = newState
                    }
                Log.i("SHOW OFFLINE DATA LOG", _homeUiState.value.toString())
            } catch (e: Exception){
                Log.e("HOMESCREENVIEWMODEL NETWORK UNAVAILABLE ERROR", e.message!!)
                _homeUiState.value = HomeUiState.Error
            }
        }
    }
}

private fun List<ProductEntity>.toProductList(): List<Product> {
    return this.map {
        Product(
            it.id, it.title, it.description, it.availability, it.brand, it.currency, it.highlights, it.images, it.main_image, it.price, it.primary_category
        )
    }
}

