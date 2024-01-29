package com.example.shopit.ui.screens.favoritesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.shopit.data.model.ProductEntity
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteScreenViewModel (
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val favoritesPager: Pager<Int, ProductEntity>
):ViewModel() {

    val favoriteProducts = favoritesPager.flow.cachedIn(viewModelScope)


//    private var _favoriteProducts: MutableStateFlow<List<ProductViewUiState>> = MutableStateFlow(listOf())
//    var favoriteProducts = _favoriteProducts.asStateFlow()
//
//    fun getFavoriteProducts(){
//        viewModelScope.launch(Dispatchers.IO) {
//            _favoriteProducts.value = try {
//                repository.getFavorites()
//            } catch (e: Exception){
//                println("An error occurred when trying to get favorite products")
//                emptyList<ProductViewUiState>()
//            }
//        }
//    }
    fun removeFromFavorites(productId: String){
        viewModelScope.launch(Dispatchers.IO) {
            localDatabaseRepository.deleteFavorite(productId)
        }
    }
}
