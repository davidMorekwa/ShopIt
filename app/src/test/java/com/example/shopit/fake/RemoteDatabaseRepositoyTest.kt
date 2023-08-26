package com.example.shopit.fake

import com.example.shopit.data.model.Product
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


private lateinit var database: FirebaseDatabase


class RemoteDatabaseRepositoyTest {
    lateinit var repo:FakeDefaultDatabaseRepository

    @Before
    fun before_test(){
        repo = FakeDefaultDatabaseRepository()
    }
    @Test
    fun remoteDatabaseRepository_getProducts_verifyProductList(){
        runBlocking {
            assertEquals(FakeDataSource.productList, repo.getInitialProducts())
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
            assertEquals(expected, actual[1])
        }
    }
}