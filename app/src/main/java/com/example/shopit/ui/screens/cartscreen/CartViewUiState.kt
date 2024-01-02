package com.example.shopit.ui.screens.cartscreen

data class CartViewUiState(
    val id: String = "",
    val title: String = "",
    val price: String = "0.0",
    var quantity: String = "0",
    val images: String = "",
    val main_image: String = ""
)
