package com.example.shopit.data.repositories

import com.example.shopit.data.model.Product

interface DataRepository {
    suspend fun getProducts(): List<Product>

}