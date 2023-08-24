package com.example.shopit.fake

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


private lateinit var database: FirebaseDatabase


class RemoteDatabaseRepositoyTest {
    @Test
    fun remoteDatabaseRepository_getProducts_verifyProductList(){
        runBlocking {
            val repository = FakeDefaultDatabaseRepository()
            assertEquals(FakeDataSource.productList, repository.getInitalProducts())
        }
    }
}