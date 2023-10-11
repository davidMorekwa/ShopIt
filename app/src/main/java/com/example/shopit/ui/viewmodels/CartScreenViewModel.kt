package com.example.shopit.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.Product
import com.example.shopit.data.network.ApiServiceRepository
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.CartViewUiState
import com.example.shopit.ui.uiStates.ProductViewUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class CartScreenViewModel(private val repository: RemoteDatabaseRepository, private val apiServiceRepository: ApiServiceRepository): ViewModel() {
    private var _cartScreenUiState:MutableStateFlow<List<CartViewUiState>> = MutableStateFlow(
        listOf()
    )
    private var _subTotal:MutableStateFlow<Double> = MutableStateFlow(12.0)
    var cartViewUiState = _cartScreenUiState.asStateFlow()
    var subTotal = _subTotal.asStateFlow()
    fun addProductToCart(product : Product){
        val cartProduct:CartViewUiState = this.toCartView(product)
        println("PRODUCT ${cartProduct.id} ADDED TO CART: Quantity = ${cartProduct.quantity}")
        viewModelScope.launch {
            repository.addProductToCart(cartProduct)
        }

    }
    private fun getSubtotal(){
        for (prod in _cartScreenUiState.value){
            _subTotal.value += (prod.price.toDouble() * prod.quantity.toDouble())
        }
        println("Cart subtotal: ${subTotal.value}")
    }

    fun getProductsInCart() {
        viewModelScope.launch(Dispatchers.IO) {
            _cartScreenUiState.value = try {
                repository.getProductsInCart()
            } catch (e: Exception) {
                println(e.message)
                emptyList<CartViewUiState>()
            }
            _subTotal.value = 0.0
            getSubtotal()
        }
    }
    fun removeProductFromCart(product: CartViewUiState){
        viewModelScope.launch {
            repository.removeProductFromCar(product)
        }
    }
    suspend fun getAccessToken(): String? {
        var token = try {
            var response = apiServiceRepository.getOAuthAccessoken()
            response.body()?.accessToken
        } catch (e: Exception){
            println("ACCESS TOKEN EXCEPTION!!!")
            println(e.message)
            null
        }
        return token
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkout(phoneNumber: String){
        viewModelScope.launch(Dispatchers.IO) {
            val token = getAccessToken()
            try {
                val response = token?.let { apiServiceRepository.makePayment(phoneNumber = phoneNumber, amount = subTotal.value.toInt().toString(), token = it) }
                println(response)
            } catch (e: Exception) {
                println("Checkout Exception $e")
            }
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
fun CartScreenViewModel.toProduct(productViewUiState: ProductViewUiState): Product {
    return Product(
        _id = productViewUiState._id.toString(),
        title = productViewUiState.title.toString(),
        price = productViewUiState.price,
        images = (productViewUiState.images?:"").toString(),
        main_image = (productViewUiState.images?.get(0))
    )
}
