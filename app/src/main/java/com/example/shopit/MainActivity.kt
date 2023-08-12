package com.example.shopit

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.shopit.data.ProductItem
import com.example.shopit.ui.theme.ShopItTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopItTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ItemList()
                }
            }
        }
    }
}



class FirebaseViewModel : ViewModel() {
    private val database = Firebase.database
    private val myRef = database.getReference("Products")

    private val _items = MutableStateFlow<List<ProductItem>>(emptyList())
    val items: StateFlow<List<ProductItem>> = _items

    init {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<ProductItem>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(ProductItem::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                for(ite in itemList){
                    println(ite._id)
                }
                _items.value = itemList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("PRODUCT READ ERROR", error.toException())
            }
        })
    }
}

@Composable
fun ItemList() {
    val viewModel: FirebaseViewModel = FirebaseViewModel()
    val items = viewModel.items.collectAsState()

    LazyColumn {
//        Text(text = "List of Products")
        items(items.value){
          Text(text = "Title: ${it.title.toString()}")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShopItTheme {

    }
}