package com.example.shopit.fake

import com.example.shopit.data.model.Product
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import com.example.shopit.ui.screens.cartscreen.CartViewUiState
import com.example.shopit.ui.screens.productscreen.ProductViewUiState
import kotlinx.coroutines.flow.Flow

class FakeDefaultRemoteDatabaseRepository: RemoteDatabaseRepository {
    override suspend fun getInitialProducts(): List<Product> {
        return FakeDataSource.remoteProductList
    }

    override suspend fun search(string: String): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun addProductToCart(product: CartViewUiState) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsInCart(): Flow<List<CartViewUiState>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeProductFromCart(product: CartViewUiState) {
        TODO("Not yet implemented")
    }

    override suspend fun filterProductsByCategory(category: String): List<Product> {
        return FakeDataSource.remoteProductList
            .filter { product: Product -> product.primary_category == category }
    }

    override suspend fun changeQuantity(productId: String, quantity: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addtoFavorites(productViewUiState: ProductViewUiState) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<ProductViewUiState> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorites(productId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }
}