package com.example.shopit.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.DatabaseRepository
import com.example.shopit.ui.uiStates.HomeUiState
import com.example.shopit.ui.uiStates.ProductViewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class HomeScreenViewModel(private val repository: DatabaseRepository):ViewModel() {
    private val productScreenViewModel: ProductScreenViewModel = ProductScreenViewModel()
    private var _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        private set
    var homeUiState = _homeUiState.asStateFlow()
    private var _productUiState = MutableStateFlow(
        ProductViewUiState()
    )
        private set
    val productUiState = _productUiState.asStateFlow()

    fun updateProductUiState(product: Product) {
        var images = product.images?.split("|") ?: emptyList()
        _productUiState.update { state ->
            state.copy(
                _id = product._id,
                title = product.title,
                description = product.description,
                images = images,
                price = product.price,
                specifications = product.speciications
            )
        }

    }

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
                 HomeUiState.Success(products)
            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                HomeUiState.Error
            }
        }
    }
}

