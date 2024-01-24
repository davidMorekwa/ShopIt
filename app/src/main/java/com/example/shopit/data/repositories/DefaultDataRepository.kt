package com.example.shopit.data.repositories

import android.util.Log
import com.example.shopit.data.model.Product
import com.example.shopit.data.network.connectionObserver.ConnectivityObserver
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.local.ProductEntity
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDataRepository @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository

): DataRepository {
    var currentNetworkStatus: ConnectivityObserver.Status = ConnectivityObserver.Status.Unavailable
    init {
        CoroutineScope(Dispatchers.Main).launch{
            connectivityObserver.observe().collectLatest{ it -> currentNetworkStatus = it }
        }
    }

    override suspend fun getProducts(): List<Product> {
        var productList: List<Product> = listOf()
        if (currentNetworkStatus == ConnectivityObserver.Status.Available){
            productList = remoteDatabaseRepository.getInitialProducts()
        } else{
            var tempList: MutableList<Product> = mutableListOf()
            localDatabaseRepository.getAllProducts().map { list ->
                for(it in list){
                    tempList.add(it.toDomainProduct())
                }
            }
            Log.i("TEMP LIST", "Local data retrieved: ${tempList.size}")
        }

    }
}