package com.example.shopit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopit.ui.screens.productscreen.ProductViewUiState

@Entity(tableName = "table_products")
data class ProductEntity(
    @PrimaryKey
    var id: String,
    var title: String,
    var description: String,
    var availability: String,
    var brand: String,
    var currency:String,
    var highlights: String,
    var images: String,
    var main_image: String,
    var price: Double,
    var primary_category: String,
    var scraped_at: String,
    var sku: Int,
    var speciications: String,
    var sub_category_1: String,
    var sub_category_2: String,
    var sub_category_3: String,
    var is_Cart: Boolean = false,
    var is_Favorite: Boolean = false
){
    fun toDomainProduct(): Product{
        return Product(
            id, title, description, availability, brand, currency, highlights, images, main_image, price, primary_category
        )
    }
    fun toProductViewUiState(product: ProductEntity): ProductViewUiState{
        var images = product.images.split(" | ")
        return ProductViewUiState(
            _id = product.id,
            title = product.title,
            description = product.description,
            images = images,
            price = product.price,
            specifications = product.speciications,
            is_Favorite = product.is_Favorite,
            is_Cart = product.is_Cart
        )
    }
}