package com.example.shopit.fake

import com.example.shopit.data.model.Product
import com.example.shopit.data.repositories.local.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDefaultLocalDatabaseRepository(): LocalDatabaseRepository {
    override suspend fun getAllProducts(): Flow<List<ProductEntity>> {
        val products = FakeDataSource.localProductList
        val productEntityList: MutableList<ProductEntity> = mutableListOf()
        for (prod in products){
            productEntityList.add(prod.toProductEntity())
        }
        return flowOf(productEntityList)
    }

    override fun getProduct(id: String): Flow<ProductEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProducts(productList: List<Product>): List<Long> {
        TODO("Not yet implemented")
    }
}