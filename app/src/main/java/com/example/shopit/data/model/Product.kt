package com.example.shopit.data.model

import com.example.shopit.data.local.ProductEntity
import com.example.shopit.ui.screens.productscreen.ProductViewUiState

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
){
    fun toProductViewUiState(product: Product): ProductViewUiState {
        var images = product.images?.split(" | ") ?: emptyList()
        return ProductViewUiState(
            _id = product._id,
            title = product.title,
            description = product.description,
            images = images,
            price = product.price,
            specifications = product.speciications
        )
    }
    fun toProductEntity(): ProductEntity{
        return ProductEntity(
            id = _id!!,
            title = title!!,
            description = description!!,
            availability = availability!!,
            brand = brand!!,
            currency = currency!!,
            highlights = highlights!!,
            images = images!!,
            price = price!!,
            primary_category = primary_category!!,
            scraped_at = scraped_at!!,
            sku = sku!!,
            speciications = speciications!!,
            sub_category_1 = sub_category_1!!,
            sub_category_2 = sub_category_2!!,
            sub_category_3 = sub_category_3!!,
            main_image = main_image!!
        )
    }
}