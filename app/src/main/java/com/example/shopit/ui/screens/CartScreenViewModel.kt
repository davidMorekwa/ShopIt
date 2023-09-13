package com.example.shopit.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.data.model.LipaNaMpesaRequest
import com.example.shopit.data.model.OAuthResponse
import com.example.shopit.data.model.Product
import com.example.shopit.data.network.ApiServiceRepository
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.CartViewUiState
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar


class CartScreenViewModel(private val repository: RemoteDatabaseRepository, private val apiServiceRepository: ApiServiceRepository): ViewModel() {
    private var _cartScreenUiState:MutableStateFlow<List<CartViewUiState>> = MutableStateFlow(
        listOf()
    )
    private var _subTotal:MutableStateFlow<Double> = MutableStateFlow(12.0)
    var cartViewUiState = _cartScreenUiState.asStateFlow()
    var subTotal = _subTotal.asStateFlow()


    fun addProductToCart(product : Product){
        val cartProduct:CartViewUiState = this.toCartView(product)
        println("PRODUCT ${cartProduct.id} ADDED TO CART: Quntity = ${cartProduct.quantity}")
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
        viewModelScope.launch() {
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
    suspend fun getAccessToken(): OAuthResponse? {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(OAuthResponse::class.java)
        var _token: OAuthResponse? = try {
            val res = apiServiceRepository.getOAuthAccessoken()
            val result = adapter.fromJson(res.body?.string())
            println("RESULT: $result")
            result
        }catch (e: Exception){
            println("Access token exception: ${e.message}")
            null
        }
        return _token
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun checkout(){
        var token = getAccessToken()?.accessToken
        println("Token: ${token}")

        viewModelScope.launch {
            try {
//                println(current.toString())
                var calendar = Calendar.getInstance()
                var tempMonth = calendar.get(Calendar.MONTH)
                var tempDate = calendar.get(Calendar.DATE)
                var tempMinute = calendar.get(Calendar.MINUTE)
                var tempSecond = calendar.get(Calendar.SECOND)

                var year = calendar.get(Calendar.YEAR)
                var month = if (tempMonth < 10) "0$tempMonth" else tempMonth
                var date = if (tempDate < 10) "0$tempDate" else tempDate
                var hour = calendar.get(Calendar.HOUR_OF_DAY)
                var minute = if (tempMinute < 10) "0$tempMinute" else tempMinute
                var second = if (tempSecond < 10) "0$tempSecond" else tempSecond
                var current = "$year$month$date$hour$minute$second"
                val request2 = LipaNaMpesaRequest(
                    partyA = 254115215356,
                    partyB = 174379,
                    phoneNumber = 254115215356,
                    businessShortCode = 174379,
                    password = "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjMwOTAyMTYyNzAw",
                    timestamp = "$current",
                    callBackURL = "https://mydomain.com/path",
                    accountReference = "ShopIt Corporation",
                    amount = subTotal.value,
                    transactionDesc = "Purchase of goods",
                    transactionType = "CustomerPayBillOnline"
                )
//                var response = apiServiceRepository.makePayment(request2, "P4EjkAvlrH2fKA4X0rYUhd8fMkuv")
//                println("Response: ${response.body?.string()}")
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
