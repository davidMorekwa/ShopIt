package com.example.shopit.data.repositories.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.shopit.data.model.CategoryEntity
import com.example.shopit.data.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Upsert
    suspend fun insertProducts(productEntityList: List<ProductEntity>): List<Long>
    @Query("SELECT * FROM table_products")
    fun getAllProducts(): PagingSource<Int, ProductEntity>
    @Query("SELECT * FROM  table_products WHERE id = :id")
    fun getProduct(id: String): Flow<ProductEntity>
    @Query("DELETE FROM table_products")
    suspend fun clearProducts()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categories: List<CategoryEntity>): List<Long>
    @Query("SELECT * FROM table_categories")
    fun getCategories(): PagingSource<Int, CategoryEntity>
    @Query("UPDATE table_products SET is_Favorite = true WHERE id = :id")
    fun addFavorites(id: String)
    @Query("SELECT * FROM table_products WHERE is_Favorite = true")
    fun getFavorites(): PagingSource<Int, ProductEntity>
    @Query("UPDATE table_products SET is_Favorite = false WHERE id = :productId")
    fun removeFavorite(productId: String)
    @Query("UPDATE table_products SET is_Cart = true WHERE id = :productId")
    fun addTOCart(productId: String)
}