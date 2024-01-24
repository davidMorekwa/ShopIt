package com.example.shopit.repository

import com.example.shopit.data.model.Product
import com.example.shopit.fake.FakeDataSource
import com.example.shopit.fake.FakeDefaultDatabaseRepository
import com.example.shopit.ui.screens.cartscreen.CartViewUiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RemoteDatabaseRepositoyTest {
    lateinit var repo: FakeDefaultDatabaseRepository

    @Before
    fun before_test(){
        repo = FakeDefaultDatabaseRepository()
    }
    @Test
    fun remoteDatabaseRepository_getProducts_verifyProductList(){
        runBlocking {
            Assert.assertEquals(FakeDataSource.productList, repo.getInitialProducts())
        }
    }
    @Test
    fun remoteDatabaseRepository_filterByCategory_verifyCategories(){
        runBlocking {
            val expected = Product(
                _id = FakeDataSource.idThree,
                main_image = FakeDataSource.urlThree,
                primary_category = "Toys",
            )
            val actual = repo.filterProductsByCategory("Toys")
            Assert.assertEquals(expected, actual[1])
        }
    }
    @Test
    fun remoteDatabaseRepository_addQuantityInCart_verifyQuantity(){
        runBlocking {
            val expected = CartViewUiState(
                id = FakeDataSource.idOne,
                title = "Sample Title",
                price = "!2.23",
                quantity = "2",
                main_image = FakeDataSource.urlOne,
                images = "Sample images"
            )
            val actual = repo.addQuantity("img1", "2")
            Assert.assertEquals(expected, actual)
        }
    }
}