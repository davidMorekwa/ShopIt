package com.example.shopit.fake

import com.example.shopit.data.model.Product

object FakeDataSource {
    const val idOne = "img1"
    const val idTwo = "img2"
    const val urlOne = "img.1"
    const val urlTwo = "img.2"
    val productList = listOf(
        Product(
            _id = idOne,
            main_image = urlOne
        ),
        Product(
            _id = idTwo,
            main_image = urlTwo
        )
    )
}