package com.example.shopit.data.local

import com.example.shopit.data.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProduct(id: String): Flow<ProductEntity>
    suspend fun upsertProducts(productList : List<Product>)

}