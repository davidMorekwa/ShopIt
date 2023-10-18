package com.example.shopit.data.remote

import com.example.shopit.data.model.Product
import com.example.shopit.ui.uiStates.CartViewUiState
import com.example.shopit.ui.uiStates.ProductViewUiState
import com.google.firebase.auth.FirebaseAuth
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
    private val favoriteRef = database.getReference("Favorites")
    private var userId = FirebaseAuth.getInstance().currentUser?.uid
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
                    println("GET PRODUCTS ERROR!!!")
                    println(error.message)
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
                    println("SEARCH ERROR!!!")
                    println(error.message)
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
                    println("FILTER PRODUCT BY CATEGORY ERROR!!!")
                    println(error.message)
                }
            })
        }
    }
    override suspend fun addProductToCart(product: CartViewUiState) {
        val id = product.id+userId
        cartRef.child(id).setValue(product)
        cartRef.child(id).child("userId").setValue(userId)
        println("Product ${product.title} added to cart document")
    }
    override suspend fun getProductsInCart():List<CartViewUiState> {
        var cartList: MutableList<CartViewUiState> = mutableListOf()
        val snapshot = cartRef.get().await()
        for (snap in snapshot.children) {
            if(snap.child("userId").value == userId){
                println(snap.child("userId"))
                println("CURRENT USER: $userId")
                var cartItem = snap.getValue<CartViewUiState>() ?: CartViewUiState()
                println("Product  retrieved from cart: ${cartItem.title}")
                cartList.add(cartItem)
            }
        }
        println("Cart size: ${cartList.size}")
        return cartList
    }
    override suspend fun removeProductFromCart(product: CartViewUiState) {
        val productId = product.id+userId
        cartRef.child(productId).removeValue()
    }
    override suspend fun changeQuantity(productId: String, quantity: String) {
        if (quantity.toInt() > 0) {
            cartRef.child(productId).child("quantity").setValue(quantity)
        } else {
            cartRef.child(productId).removeValue()
        }
    }
    override suspend fun addtoFavorites(productViewUiState: ProductViewUiState) {
        productViewUiState._id?.let {
            favoriteRef.child(it).setValue(productViewUiState)
            favoriteRef.child(it).child("userId").setValue(userId)
        }
        println("Product ${productViewUiState.title} has been added to favorites")
    }

    override suspend fun getFavorites(): List<ProductViewUiState> {
        val favoriteProductsList: MutableList<ProductViewUiState> = mutableListOf()
        val snapshot = favoriteRef.get().await()
        for(snap in snapshot.children){
            if(snap.child("userId").value == userId){
                var favoriteProductItem = snap.getValue<ProductViewUiState>() ?: ProductViewUiState()
                println("Product retrieved from favorites: ${favoriteProductItem.title}")
                favoriteProductsList.add(favoriteProductItem)
            }
        }
        return favoriteProductsList
    }

    override suspend fun clearCart() {
        val snapshot = cartRef.get().await()
//        for(snap in snapshot.children){
//            if (snap.child("userId").value == userId){
//                snap.
//            }
//        }
    }
}