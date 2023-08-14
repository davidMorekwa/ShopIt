package com.example.shopit.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.DatabaseRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HomeUiState {
    object Error : HomeUiState
    object Loading : HomeUiState
    data class Success(val products: List<Product>) : HomeUiState
}

class HomeScreenViewModel(private val repository: DatabaseRepository):ViewModel() {
    private var _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
//    private var _homeUiState:HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var homeUiState = _homeUiState.asStateFlow()

    init {
        println("Getting products")
        getInitialProducts()
    }

    fun getInitialProducts() {
        viewModelScope.launch {
//            val product= repository.getInitalProducts()
//            println("TITLE: ${product[1].title}")
            _homeUiState.value = try{
                var products = repository.getInitalProducts()
                println("PRODUCTS ${products.size}")
                for (p in products){
                    println(p.title)
                }
                 HomeUiState.Success(products)
            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                HomeUiState.Error
            }
        }
    }
}

