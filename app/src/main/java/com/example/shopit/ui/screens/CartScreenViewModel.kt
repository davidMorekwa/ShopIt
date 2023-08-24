package com.example.shopit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.CartViewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartScreenViewModel(private val repository: RemoteDatabaseRepository): ViewModel() {
    private var _cartScreenUiState:MutableStateFlow<List<CartViewUiState>> = MutableStateFlow(
        listOf()
    )
    private var _subTotal:MutableStateFlow<Double> = MutableStateFlow(0.0)
    var cartViewUiState = _cartScreenUiState.asStateFlow()
    var subTotal = _subTotal.asStateFlow()



    fun addProductToCart(product : Product){
        val cartProduct:CartViewUiState = this.toCartView(product)
        println("PRODUCT ${cartProduct.id} ADDED TO CART: Quntity = ${cartProduct.quantity}")
        viewModelScope.launch {
            repository.addProductToCart(cartProduct)
        }
    }

    fun getProductsInCart() {
        viewModelScope.launch() {
            _cartScreenUiState.value = try {
                repository.getProductsInCart()
            } catch (e: Exception) {
                println(e.message)
                emptyList<CartViewUiState>()
            }
            for (prod in _cartScreenUiState.value){
                _subTotal.value += prod.price.toDouble()
            }
            println("Cart subtotal: ${subTotal.value}")
        }
    }
    fun removeProductFromCart(product: CartViewUiState){
        viewModelScope.launch {
            repository.removeProductFromCar(product)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
fun CartScreenViewModel.toCartView(product: Product): CartViewUiState{
    return CartViewUiState(
        id = product._id.toString(),
        title = product.title.toString(),
        price = product.price.toString(),
        quantity = "1",
        images = product.images?:"",
        main_image = product.main_image?:""
    )
}
