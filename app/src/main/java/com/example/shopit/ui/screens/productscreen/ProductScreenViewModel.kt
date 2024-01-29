package com.example.shopit.ui.screens.productscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.ProductEntity
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProductScreenViewModel (
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
):ViewModel() {
    private val _uiState: MutableStateFlow<ProductViewUiState> = MutableStateFlow(ProductViewUiState())
    val uiState = _uiState.asStateFlow()
    fun addToFavorites(productViewUiState: ProductViewUiState){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localDatabaseRepository.addToFavorites(productViewUiState._id!!)
            } catch (e: Exception){
                println("Add to favorites exception!!!")
            }
        }
    }
    fun getProduct(product: ProductEntity){
        _uiState.value = product.toProductViewUiState(product)
    }
}

