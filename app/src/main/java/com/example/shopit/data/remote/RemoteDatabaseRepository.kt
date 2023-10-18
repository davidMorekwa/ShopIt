package com.example.shopit.data.remote

import com.example.shopit.data.model.Product
import com.example.shopit.ui.uiStates.CartViewUiState
import com.example.shopit.ui.uiStates.ProductViewUiState

interface RemoteDatabaseRepository {
    suspend fun getInitialProducts() : List<Product>
    suspend fun search(string: String): List<Product>
    suspend fun addProductToCart(product: CartViewUiState)
    suspend fun getProductsInCart():List<CartViewUiState>
    suspend fun removeProductFromCart(product: CartViewUiState)
    suspend fun filterProductsByCategory(category: String):List<Product>
    suspend fun changeQuantity(productId: String, quantity: String)
    suspend fun addtoFavorites(productViewUiState: ProductViewUiState)
    suspend fun getFavorites(): List<ProductViewUiState>
    suspend fun clearCart()
}