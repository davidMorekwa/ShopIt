package com.example.shopit.data.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(productEntityList: List<ProductEntity>): List<Long>
    @Query("SELECT * FROM table_products LIMIT 10")
    fun getAllProducts(): Flow<List<ProductEntity>>
    @Query("SELECT * FROM  table_products WHERE id = :id")
    fun getProduct(id: String): Flow<ProductEntity>
}