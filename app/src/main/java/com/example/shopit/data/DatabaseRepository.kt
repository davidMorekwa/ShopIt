package com.example.shopit.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.shopit.data.model.Product
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface DatabaseRepository {
//    TODO: Make suspend function
    suspend fun getInitalProducts() : List<Product>
}

class DefaultDatabaseRepository(private val database: FirebaseDatabase) : DatabaseRepository {
    private val productsRef = database.getReference("Products")
    override suspend fun getInitalProducts(): List<Product> {

        return suspendCoroutine { continuation: Continuation<List<Product>> ->
            var productList:MutableList<Product> = mutableListOf()
            productsRef.limitToFirst(20).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        for (productSnapshot in snapshot.children) { // Assuming 20 is the maximum index
                            var product = Product(
                                _id = productSnapshot.child("_id").getValue(String::class.java),
                                title = productSnapshot.child("title").getValue(String::class.java),
                                description = productSnapshot.child("description")
                                    .getValue(String::class.java),
                                availability = productSnapshot.child("availability")
                                    .getValue(String::class.java),
                                brand = productSnapshot.child("brand").getValue(String::class.java),
                                currency = productSnapshot.child("currency")
                                    .getValue(String::class.java),
                                highlights = productSnapshot.child("highlights")
                                    .getValue(String::class.java),
                                main_image = productSnapshot.child("main_image")
                                    .getValue(String::class.java),
                                price = productSnapshot.child("price").getValue(Double::class.java),
                                primary_category = productSnapshot.child("primary_category")
                                    .getValue(String::class.java),
                                scraped_at = productSnapshot.child("scraped_at")
                                    .getValue(String::class.java),
                                sku = productSnapshot.child("sku").getValue(Int::class.java),
                                speciications = productSnapshot.child("specifications")
                                    .getValue(String::class.java),
                                sub_category_1 = productSnapshot.child("sub_category_1")
                                    .getValue(String::class.java),
                                sub_category_2 = productSnapshot.child("sub_category_2")
                                    .getValue(String::class.java),
                                sub_category_3 = productSnapshot.child("sub_category_3")
                                    .getValue(String::class.java)
                            )
                            productList.add(product)
                        }
                    }
                    continuation.resume(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                    Log.e("DATABASE_ERROR", error.message)
                }
            })
        }

    }
}