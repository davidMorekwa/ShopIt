package com.example.shopit.fake

import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.CartViewUiState

class FakeDefaultDatabaseRepository: RemoteDatabaseRepository {
    override suspend fun getInitialProducts(): List<Product> {
        return FakeDataSource.productList
    }

    override suspend fun search(string: String): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun addProductToCart(product: CartViewUiState) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsInCart(): List<CartViewUiState> {
        TODO("Not yet implemented")
    }

    override suspend fun removeProductFromCar(product: CartViewUiState) {
        TODO("Not yet implemented")
    }

    override suspend fun filterProductsByCategory(category: String): List<Product> {
        return FakeDataSource.productList
            .filter { product: Product -> product.primary_category == category }
    }
}