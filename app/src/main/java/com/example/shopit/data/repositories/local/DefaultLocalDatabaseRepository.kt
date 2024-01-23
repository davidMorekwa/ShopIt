package com.example.shopit.data.repositories.local

import com.example.shopit.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultLocalDatabaseRepository @Inject constructor(
    private val productsDao: ProductsDao
): LocalDatabaseRepository {
    override suspend fun getAllProducts(): Flow<List<ProductEntity>> {
        return productsDao.getAllProducts()
    }

    override fun getProduct(id: String): Flow<ProductEntity> {
        return productsDao.getProduct(id)
    }

    override suspend fun insertProducts(productList: List<Product>): List<Long> {
        val productEntityList: MutableList<ProductEntity> = mutableListOf()
        for (product in productList){
            productEntityList.add(product.toProductEntity())
        }
        return productsDao.insertProducts(productEntityList.toList())
    }

}