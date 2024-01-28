package com.example.shopit.ui.screens.homescreen

import com.example.shopit.data.model.Product
import com.example.shopit.data.network.connectionObserver.ConnectivityObserver

sealed interface HomeUiState {
    object Error : HomeUiState
    object Loading : HomeUiState
    data class Success(val products: List<Product>) : HomeUiState
}

data class NetworkState(
    val status: ConnectivityObserver.Status
)
