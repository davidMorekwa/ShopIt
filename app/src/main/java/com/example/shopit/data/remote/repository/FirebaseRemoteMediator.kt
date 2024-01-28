package com.example.shopit.data.remote.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.shopit.data.local.LocalDatabaseRepository
import com.example.shopit.data.local.ShopitDatabase
import com.example.shopit.data.model.CategoryEntity
import com.example.shopit.data.model.ProductEntity

@OptIn(ExperimentalPagingApi::class)
class FirebaseRemoteMediator(
    private val shopitDatabase: ShopitDatabase,
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) : RemoteMediator<Int, ProductEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        return try {
            val pageSize = state.config.pageSize
            val TAG = "REMOTE MEDIATOR"
            val lastItemId = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()?.id
                }
            }
            Log.d(TAG, "Last item id : ${lastItemId}")
            val products = remoteDatabaseRepository.getInitialProducts(
                pageSize = state.config.pageSize,
                lastItemId = lastItemId
            )
            Log.d(TAG, "Products ${products.size}")
            Log.d(TAG, "Adding data to room")
            shopitDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.d(TAG, "Clearing Database")
                    localDatabaseRepository.clearProducts()
                }
                Log.d(TAG, "Insert beers to room")
                val categories = products.map { it-> CategoryEntity(name = it.primary_category!!, image = it.main_image!!) }
                localDatabaseRepository.insertProducts(products.map { it.toProductEntity() })
                localDatabaseRepository.insertCategories(categories)
            }

            MediatorResult.Success(endOfPaginationReached = products.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}