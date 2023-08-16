package com.example.shopit.data.model

data class Product(
    var _id: String? = "",
    var title: String? = "",
    var description: String? = "",
    var availability: String? = "",
    var brand: String? = "",
    var currency:String? = "",
    var highlights: String? = "",
    var images: String? = "",
    var main_image: String? = "",
    var price: Double? = 0.0,
    var primary_category: String? = "",
    var scraped_at: String? = "",
    var sku: Int? = 0,
    var speciications: String? = "",
    var sub_category_1: String? = "",
    var sub_category_2: String? = "",
    var sub_category_3: String? = "",
)