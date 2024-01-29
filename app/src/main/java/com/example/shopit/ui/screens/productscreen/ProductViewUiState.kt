package com.example.shopit.ui.screens.productscreen

data class ProductViewUiState(
    val _id: String? = "",
    val title: String? = "",
    val description: String? = "",
    val images: List<String>? = emptyList(),
    val price: Double? = 0.0,
    val specifications: String? = "",
    val is_Favorite: Boolean = false,
    val is_Cart: Boolean = false
)