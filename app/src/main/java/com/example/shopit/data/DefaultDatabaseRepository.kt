package com.example.shopit.data

import android.util.Log
import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.uiStates.CartViewUiState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine



class DefaultDatabaseRepository(private val database: FirebaseDatabase) : RemoteDatabaseRepository {
    private val productsRef = database.getReference("Products")
    private val cartRef = database.getReference("Cart")
    override suspend fun getInitialProducts(): List<Product> {
        return suspendCoroutine { continuation: Continuation<List<Product>> ->
            var productList:MutableList<Product> = mutableListOf()
            productsRef.limitToFirst(60).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        for (productSnapshot in snapshot.children) { // Assuming 20 is the maximum index
                            var product = productSnapshot.getValue<Product>()
                            product?.let { product -> productList.add(product) }
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
                            var product = snap.getValue<Product>()
                            searchResult.add(product!!)
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
    override suspend fun filterProductsByCategory(category: String): List<Product> {
        return suspendCoroutine { continuation: Continuation<List<Product>> ->
            var filterResult: MutableList<Product> = mutableListOf()
            productsRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children){
                        if(snap.child("primary_category").value.toString().contains(category)){
                            var product = snap.getValue<Product>()
                            filterResult.add(product!!)
                        }
                    }
                    continuation.resume(filterResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
    override suspend fun addProductToCart(product: CartViewUiState) {
        cartRef.child(product.id).setValue(product)
        println("Product ${product.title} added to cart document")
    }
    override suspend fun getProductsInCart():List<CartViewUiState> {
        var cartList: MutableList<CartViewUiState> = mutableListOf()
        val snapshot = cartRef.get().await()
        for (snap in snapshot.children) {
            var cartItem = snap.getValue<CartViewUiState>() ?: CartViewUiState()
            println("Product  retrieved from cart: ${cartItem.title}")
            cartList.add(cartItem)
        }
        println("Cart size: ${cartList.size}")
        return cartList
    }
    override suspend fun removeProductFromCar(product: CartViewUiState) {
        cartRef.child(product.id).removeValue()
    }
    override suspend fun addQuantity(productId: String, quantity: String) {
        TODO("Not yet implemented")

    }
    override suspend fun reduceQuantity(productId: String, quantity: String) {
        TODO("Not yet implemented")

    }
}