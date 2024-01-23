package com.example.shopit.data.repositories.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shopit.data.model.Product
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class FirebasePagingSource(
    private val databaseReference: DatabaseReference
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            val currentPage = params.key?:0
            val pageSize = params.loadSize
            val snapshot = databaseReference
                .limitToFirst(pageSize)
                .startAt(
                    currentPage * pageSize.toDouble()
                )
                .get()
                .await()
            val data = mutableListOf<Product>()
            for (snap in snapshot.children){
                val product = snap.getValue(Product::class.java)
                product?.let { data.add(it) }
            }
            val previousKey = if(currentPage == 0) null else currentPage-1
            val nextKey = if(snapshot.childrenCount.toInt() < pageSize) null else currentPage+1

            return LoadResult.Page(
                data = data,
                prevKey = previousKey,
                nextKey = nextKey
            )
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
}