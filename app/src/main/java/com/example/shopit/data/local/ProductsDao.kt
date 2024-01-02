package com.example.shopit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert
    fun insertProducts(productEntity: ProductEntity)
    @Query("SELECT * FROM table_products")
    fun getAllProducts(): Flow<List<ProductEntity>>
    @Query("SELECT * FROM  table_products WHERE id = :id")
    fun getProduct(id: String): Flow<ProductEntity>
}