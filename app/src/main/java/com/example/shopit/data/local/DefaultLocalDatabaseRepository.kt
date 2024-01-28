package com.example.shopit.data.local

import android.util.Log
import androidx.paging.PagingSource
import com.example.shopit.data.model.CategoryEntity
import com.example.shopit.data.model.ProductEntity
import kotlinx.coroutines.flow.Flow
const val TAG = "LOCAL DB REPOSITORY"
class DefaultLocalDatabaseRepository(
    private val productsDao: ProductsDao
): LocalDatabaseRepository {

    override fun getAllProducts(): PagingSource<Int, ProductEntity> {
        Log.d(TAG, "Getting products from local db")
        return productsDao.getAllProducts()
    }

    override fun getProduct(id: String): Flow<ProductEntity> {
        return productsDao.getProduct(id)
    }

    override suspend fun insertProducts(productList: List<ProductEntity>): List<Long> {
        Log.d(TAG, "Inserting products into local db")
        return productsDao.insertProducts(productList)
    }

    override suspend fun clearProducts() {
        Log.d(TAG, "Deleting products from local db")
        productsDao.clearProducts()
    }

    override suspend fun insertCategories(categories: List<CategoryEntity>): List<Long> {
        val res = productsDao.insertCategory(categories)
        Log.d(TAG, "Categories inserted: ${res.size}")
        return res
    }

    override fun getCategories(): PagingSource<Int, CategoryEntity> {
        return productsDao.getCategories()
    }
}