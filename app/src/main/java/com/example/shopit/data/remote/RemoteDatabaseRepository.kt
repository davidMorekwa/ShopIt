package com.example.shopit.data.remote

import com.example.shopit.data.model.Product
import com.example.shopit.ui.uiStates.CartViewUiState

interface RemoteDatabaseRepository {
    suspend fun getInitalProducts() : List<Product>
    suspend fun search(string: String): List<Product>
    suspend fun addProductToCart(product: CartViewUiState)
    suspend fun getProductsInCart():List<CartViewUiState>
    suspend fun removeProductFromCar(product: CartViewUiState)
}