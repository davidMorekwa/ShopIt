package com.example.shopit.fake

import com.example.shopit.data.model.Product

object FakeDataSource {
    const val idOne = "img1"
    const val idTwo = "img2"
    const val idThree = "img3"
    const val idFour = "img4"
    const val urlOne = "img.1"
    const val urlTwo = "img.2"
    const val urlThree = "img.3"
    const val urlFour = "img.4"
    val productList = listOf(
        Product(
            _id = idOne,
            main_image = urlOne,
            primary_category = "Toys"
        ),
        Product(
            _id = idTwo,
            main_image = urlTwo,
            primary_category = "Electronics"
        ),
        Product(
            _id = idThree,
            main_image = urlThree,
            primary_category = "Toys",
        ),
        Product(
            _id = idFour,
            main_image = urlFour,
            primary_category = "Electronics",
        )

    )
}