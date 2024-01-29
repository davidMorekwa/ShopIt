package com.example.shopit.ui.screens.homescreen


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.shopit.data.model.Product
import com.example.shopit.data.model.ProductEntity
import com.example.shopit.data.network.darajaApi.PreferenceKeys
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/*
TODO: Create a DataRepository class that will get data from the local and remote repositories
 */

class HomeScreenViewModel(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val dataStore: DataStore<Preferences>,
//    private val localDatabaseRepository: LocalDatabaseRepository,
    private val roomPager: Pager<Int, ProductEntity>
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
        viewModelScope.launch {
            _toggleSwitchState.value = dataStore.data
                .map { value: Preferences -> value[PreferenceKeys.USE_DARK_THEME] ?: false }
                .first()
        }
    }
    fun changeTheme(status: Boolean){
        viewModelScope.launch {
            _toggleSwitchState.value = status
            dataStore.edit { mutablePreferences: MutablePreferences ->
                mutablePreferences[PreferenceKeys.USE_DARK_THEME] = status
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

