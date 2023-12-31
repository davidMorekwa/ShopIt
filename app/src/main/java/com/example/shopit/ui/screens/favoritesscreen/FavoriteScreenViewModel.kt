package com.example.shopit.ui.screens.favoritesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository
import com.example.shopit.ui.screens.productscreen.ProductViewUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(private val repository: RemoteDatabaseRepository):ViewModel() {
    private var _favoriteProducts: MutableStateFlow<List<ProductViewUiState>> = MutableStateFlow(listOf())
    var favoriteProducts = _favoriteProducts.asStateFlow()

    fun getFavoriteProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteProducts.value = try {
                repository.getFavorites()
            } catch (e: Exception){
                println("An error occurred when trying to get favorite products")
                emptyList<ProductViewUiState>()
            }
        }
    }
    fun removeFromFavorites(productId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(productId)
            getFavoriteProducts()
        }
    }
}
