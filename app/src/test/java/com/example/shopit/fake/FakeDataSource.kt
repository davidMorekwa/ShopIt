package com.example.shopit.fake

import com.example.shopit.data.model.OAuthResponse
import com.example.shopit.data.model.Product
import com.example.shopit.ui.screens.cartscreen.CartViewUiState

object FakeDataSource {
    const val idOne = "img1"
    const val idTwo = "img2"
    const val idThree = "img3"
    const val idFour = "img4"
    const val idFive = "img5"
    const val idSix = "img6"
    const val idSeven = "img7"
    const val idEight = "img8"
    const val urlOne = "img.1"
    const val urlTwo = "img.2"
    const val urlThree = "img.3"
    const val urlFour = "img.4"
    const val urlFive = "img.5"
    const val urlSix = "img.6"
    const val urlSeven = "img.7"
    const val urlEight = "img.8"
    val remoteProductList = listOf(
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
    val localProductList = listOf(
        Product(
            _id = idFive,
            main_image = urlFive,
            primary_category = "Toys"
        ),
        Product(
            _id = idSix,
            main_image = urlSix,
            primary_category = "Electronics"
        ),
        Product(
            _id = idSeven,
            main_image = urlSeven,
            primary_category = "Toys",
        ),
        Product(
            _id = idEight,
            main_image = urlEight,
            primary_category = "Electronics",
        )
    )
    val cart = listOf<CartViewUiState>(
        CartViewUiState(
            id = idOne,
            title = "Sample Title",
            price = "!2.23",
            quantity = "1",
            main_image = urlOne,
            images = "Sample images"
        ),
        CartViewUiState(
            id = idTwo,
            title = "Sample Title",
            price = "!2.23",
            quantity = "1",
            main_image = urlTwo,
            images = "Sample images"
        )
    )
    val response = OAuthResponse(
        accessToken = "ThisIsASampleAccessToken",
        expiresIn = "3600"
    )
}