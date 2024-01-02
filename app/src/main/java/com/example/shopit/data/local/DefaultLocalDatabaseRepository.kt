package com.example.shopit.data.local

import com.example.shopit.data.model.Product
import kotlinx.coroutines.flow.Flow

class DefaultLocalDatabaseRepository(
    private val productsDao: ProductsDao
): LocalDatabaseRepository {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return productsDao.getAllProducts()
    }

    override fun getProduct(id: String): Flow<ProductEntity> {
        return productsDao.getProduct(id)
    }

    override suspend fun upsertProducts(productList: List<Product>) {
//        TODO
    }

}