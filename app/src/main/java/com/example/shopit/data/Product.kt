package com.example.shopit.data

data class ProductItem(
    val _id: String? = "",
    val title: String? = "",
    val description: String? = "",
    val availability: String? = "",
    val brand: String? = "",
    val currency:String? = "",
    val highlights: String? = "",
    val images: List<String>? = emptyList(),
    val main_image: String? = "",
    val price: Double? = 0.0,
    val primary_category: String? = "",
    val scraped_at: String? = "",
    val sku: Int? = 0,
    val speciications: String? = "",
    val sub_category_1: String? = "",
    val sub_category_2: String? = "",
    val sub_category_3: String? = "",
)