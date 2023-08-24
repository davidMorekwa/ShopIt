package com.example.shopit.fake

import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.CartViewUiState

class FakeRemoteDatabase: RemoteDatabaseRepository {
    override suspend fun getInitalProducts(): List<Product> {
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
}