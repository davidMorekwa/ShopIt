package com.example.shopit.data.remote.repository

import com.example.shopit.data.model.Product
import com.example.shopit.ui.screens.cartscreen.CartViewUiState
import com.example.shopit.ui.screens.productscreen.ProductViewUiState
import kotlinx.coroutines.flow.Flow

interface RemoteDatabaseRepository {
    suspend fun getInitialProducts() : List<Product>
    suspend fun search(string: String): List<Product>
    suspend fun addProductToCart(product: CartViewUiState)
    suspend fun removeProductFromCart(product: CartViewUiState)
    suspend fun filterProductsByCategory(category: String):List<Product>
    suspend fun changeQuantity(productId: String, quantity: String)
    suspend fun addtoFavorites(productViewUiState: ProductViewUiState)
    suspend fun getFavorites(): List<ProductViewUiState>
    suspend fun removeFromFavorites(productId: String)
    suspend fun clearCart()
    suspend fun getProductsInCart(): Flow<List<CartViewUiState>>
}