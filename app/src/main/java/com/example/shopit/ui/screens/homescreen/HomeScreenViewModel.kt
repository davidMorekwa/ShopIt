package com.example.shopit.ui.screens.homescreen

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.shopit.data.model.ProductEntity
import com.example.shopit.data.remote.darajaApi.PreferenceKeys
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    private val repository: RemoteDatabaseRepository,
    private val dataStore: DataStore<Preferences>,
//    private val localDatabaseRepository: LocalDatabaseRepository,
    private val roomPager: Pager<Int, ProductEntity>,
    private val context: Context
):ViewModel() {
    val res = roomPager.flow
        .cachedIn(viewModelScope)

    private var _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        private set
    var homeUiState = _homeUiState.asStateFlow()
    private var _toggleSwitchState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val toggleSwitchState = _toggleSwitchState.asStateFlow()

    init {
        getTheme()
        println("Getting products")
//        getProductCategories(products = res)
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
//    fun getInitialProducts() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try{
////                var products = repository.getInitialProducts()
////                println("PRODUCTS ${products.size}")
//////                storeLocally(products)
////                _homeUiState.value = HomeUiState.Success(products.shuffled())
//
//            } catch (e: IOException){
//                Log.e("ERROR", e.message.toString())
//                _homeUiState.value = HomeUiState.Error
//            }
//            _categoryList.value = getCategories(_homeUiState.value)
//        }
//    }
//    fun filterProductsByCategory(category: String){
//        viewModelScope.launch(Dispatchers.IO) {
//            if(category == "All"){
//                getInitialProducts()
//            }
//            _homeUiState.value = try{
//                var products = repository.filterProductsByCategory(category)
//                println("PRODUCTS IN CATEGORY $category: ${products.size}")
//                HomeUiState.Success(products)
//            } catch (e: IOException){
//                Log.e("ERROR", e.message.toString())
//                HomeUiState.Error
//            }
//        }
//    }
    fun networkUnavailable(){
        _homeUiState.value = HomeUiState.Error
    }
}

