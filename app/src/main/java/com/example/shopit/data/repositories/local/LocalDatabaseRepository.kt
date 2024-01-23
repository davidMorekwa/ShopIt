package com.example.shopit.data.repositories.local

import com.example.shopit.data.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    suspend fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProduct(id: String): Flow<ProductEntity>
    suspend fun insertProducts(productList : List<Product>): List<Long>

}