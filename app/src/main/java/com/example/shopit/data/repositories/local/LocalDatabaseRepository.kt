package com.example.shopit.data.repositories.local

import androidx.paging.PagingSource
import com.example.shopit.data.model.CategoryEntity
import com.example.shopit.data.model.ProductEntity
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    fun getAllProducts(): PagingSource<Int, ProductEntity>
    fun getProduct(id: String): Flow<ProductEntity>
    suspend fun insertProducts(productList : List<ProductEntity>): List<Long>
    suspend fun clearProducts()
    suspend fun insertCategories(categories: List<CategoryEntity>): List<Long>
    fun getCategories(): PagingSource<Int, CategoryEntity>
}