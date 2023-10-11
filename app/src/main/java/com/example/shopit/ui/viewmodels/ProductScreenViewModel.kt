package com.example.shopit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.ProductViewUiState
import kotlinx.coroutines.launch

class ProductScreenViewModel(private val repository: RemoteDatabaseRepository):ViewModel() {
    fun addToFavorites(productViewUiState: ProductViewUiState){
        viewModelScope.launch {
            try {
                repository.addtoFavorites(productViewUiState)
            } catch (e: Exception){
                println("Add to favorites exception!!!")
            }
        }
    }
}

