package com.example.shopit.data.local

import androidx.room.Entity
import com.example.shopit.data.model.Product

@Entity(tableName = "table_products")
data class ProductEntity(
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
){
    fun toDomainProduct(): Product{
        return Product(
            id, title, description, availability, brand, currency, highlights, images, main_image, price, primary_category
        )
    }
}