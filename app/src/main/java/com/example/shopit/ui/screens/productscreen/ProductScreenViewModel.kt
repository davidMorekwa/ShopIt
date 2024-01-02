package com.example.shopit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.remote.repository.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.ProductViewUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductScreenViewModel(private val repository: RemoteDatabaseRepository):ViewModel() {
    private val _uiState: MutableStateFlow<ProductViewUiState> = MutableStateFlow(ProductViewUiState())
    val uiState = _uiState.asStateFlow()
    fun addToFavorites(productViewUiState: ProductViewUiState){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addtoFavorites(productViewUiState)
            } catch (e: Exception){
                println("Add to favorites exception!!!")
            }
        }
    }
    fun getProduct(productViewUiState: ProductViewUiState){
        _uiState.value = productViewUiState
    }
}

