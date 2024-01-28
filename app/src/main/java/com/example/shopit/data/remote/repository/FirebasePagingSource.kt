package com.example.shopit.data.remote.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shopit.data.local.ShopitDatabase
import com.example.shopit.data.model.Product
import com.google.firebase.database.DatabaseReference


class FirebasePagingSource(
    private val databaseReference: DatabaseReference,
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val shopitDatabase: ShopitDatabase
) : PagingSource<String, Product>() {
    var categories:MutableList<String> = mutableListOf()
    override fun getRefreshKey(state: PagingState<String, Product>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Product> {
        return try {
            println("Loading data from paging source")
            val startAfter = params.key
            val pageSize = params.loadSize
            val data = remoteDatabaseRepository.getInitialProducts(pageSize = pageSize, lastItemId = startAfter )
            println("Data from Firebase: ${data.size}")
            if (data.size!=0){
                var prodEntities = data.map { it -> it.toProductEntity() }

                    data.map { it ->
                        if(!categories.contains(it.primary_category)){
                            categories.add(it.primary_category.toString())
                        }
                    }
                var l = shopitDatabase.productsDao().insertProducts(prodEntities)
                Log.d("FIREBASE PAGING SOURCE", "Products inserted ${l.size}")
                Log.d("FIREBASE PAGING SOURCE", "Categories ${categories.size}")
            }
            Log.d("FIREBASE PAGING SOURCE", "Final Categories ${categories.size}")
            val lastVisibleItem = data.lastOrNull()?._id
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = lastVisibleItem
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}