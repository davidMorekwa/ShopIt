package com.example.shopit.data

import android.util.Log
import com.example.shopit.data.model.Product
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
    suspend fun search(string: String): List<Product>
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
                                _id = productSnapshot.child("_id")
                                    .getValue(String::class.java),
                                title = productSnapshot.child("title")
                                    .getValue(String::class.java),
                                description = productSnapshot.child("description")
                                    .getValue(String::class.java),
                                availability = productSnapshot.child("availability")
                                    .getValue(String::class.java),
                                brand = productSnapshot.child("brand")
                                    .getValue(String::class.java),
                                currency = productSnapshot.child("currency")
                                    .getValue(String::class.java),
                                highlights = productSnapshot.child("highlights")
                                    .getValue(String::class.java),
                                main_image = productSnapshot.child("main_image")
                                    .getValue(String::class.java),
                                images = productSnapshot.child("images")
                                    .getValue(String::class.java),
                                price = productSnapshot.child("price")
                                    .getValue(Double::class.java),
                                primary_category = productSnapshot.child("primary_category")
                                    .getValue(String::class.java),
                                scraped_at = productSnapshot.child("scraped_at")
                                    .getValue(String::class.java),
                                sku = productSnapshot.child("sku")
                                    .getValue(Int::class.java),
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
                            println("IMAGES: ${product.images}")
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

    override suspend fun search(searchString: String): List<Product> {
        return suspendCoroutine { continuation: Continuation<List<Product>> ->
            var searchResult: MutableList<Product> = mutableListOf()
            productsRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children){
                        if(snap.child("title").value.toString().contains(searchString) || snap.child("brand").value.toString().contains(searchString) || snap.child("primary_category").value.toString().contains(searchString)){
                            var product = Product(
                                _id = snap.child("_id")
                                    .getValue(String::class.java),
                                title = snap.child("title")
                                    .getValue(String::class.java),
                                description = snap.child("description")
                                    .getValue(String::class.java),
                                availability = snap.child("availability")
                                    .getValue(String::class.java),
                                brand = snap.child("brand")
                                    .getValue(String::class.java),
                                currency = snap.child("currency")
                                    .getValue(String::class.java),
                                highlights = snap.child("highlights")
                                    .getValue(String::class.java),
                                main_image = snap.child("main_image")
                                    .getValue(String::class.java),
                                images = snap.child("images")
                                    .getValue(String::class.java),
                                price = snap.child("price")
                                    .getValue(Double::class.java),
                                primary_category = snap.child("primary_category")
                                    .getValue(String::class.java),
                                scraped_at = snap.child("scraped_at")
                                    .getValue(String::class.java),
                                sku = snap.child("sku")
                                    .getValue(Int::class.java),
                                speciications = snap.child("specifications")
                                    .getValue(String::class.java),
                                sub_category_1 = snap.child("sub_category_1")
                                    .getValue(String::class.java),
                                sub_category_2 = snap.child("sub_category_2")
                                    .getValue(String::class.java),
                                sub_category_3 = snap.child("sub_category_3")
                                    .getValue(String::class.java)
                            )
                            searchResult.add(product)
                            println("SEARCH PRODUCT: ${product.title}")
                        }
                    }
                    continuation.resume(searchResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}