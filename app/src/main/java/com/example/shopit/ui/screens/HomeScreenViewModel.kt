package com.example.shopit.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.HomeUiState
import com.example.shopit.ui.uiStates.ProductViewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


class HomeScreenViewModel(private val repository: RemoteDatabaseRepository):ViewModel() {
    private var _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        private set
    var homeUiState = _homeUiState.asStateFlow()
    private var _productUiState = MutableStateFlow(
        ProductViewUiState()
    )
        private set
    val productUiState = _productUiState.asStateFlow()
    private var _categoryList: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val categoryList = _categoryList.asStateFlow()

    init {
        println("Getting products")
        getInitialProducts()
    }
    fun getCategories(homeUiState: HomeUiState): List<String>{
        var tempCategoryList: MutableList<String> = mutableListOf("All")
        if (homeUiState is HomeUiState.Success){
            for (product in (_homeUiState.value as HomeUiState.Success).products){
                if(!tempCategoryList.contains(product.primary_category.toString())){
                    tempCategoryList.add(product.primary_category.toString())
                }
            }
        } else {
            return emptyList()
        }
        println("categories size: ${tempCategoryList.size}")
        return tempCategoryList.toList()
    }
    fun getInitialProducts() {
        viewModelScope.launch {
            _homeUiState.value = try{
                var products = repository.getInitialProducts()
                println("PRODUCTS ${products.size}")
                HomeUiState.Success(products)
            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                HomeUiState.Error
            }
            _categoryList.value = getCategories(_homeUiState.value)
        }
    }
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
    fun filterProductsByCategory(category: String){
        viewModelScope.launch {
            if(category == "All"){
                getInitialProducts()
            }
            _homeUiState.value = try{
                var products = repository.filterProductsByCategory(category)
                println("PRODUCTS IN CATEGORY $category: ${products.size}")
                HomeUiState.Success(products)
            } catch (e: IOException){
                Log.e("ERROR", e.message.toString())
                HomeUiState.Error
            }
        }
    }
}

