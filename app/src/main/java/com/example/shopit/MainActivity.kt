package com.example.shopit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopit.ui.screens.HomeScreen
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.theme.ShopItTheme

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
                    val viewModel:HomeScreenViewModel = viewModel(factory = viewModelProvider.factory)
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
    }
}



//class FirebaseViewModel : ViewModel() {
//    private val database = Firebase.database
//    private val myRef = database.getReference("Products")
//
//    private val _items = MutableStateFlow<List<Product>>(emptyList())
//    val items: StateFlow<List<Product>> = _items
//
//    init {
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val itemList = mutableListOf<Product>()
//                for (itemSnapshot in snapshot.children) {
//                    val item = itemSnapshot.getValue(Product::class.java)
//                    item?.let {
//                        itemList.add(it)
//                    }
//                }
//                for(ite in itemList){
//                    println(ite._id)
//                }
//                _items.value = itemList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("PRODUCT READ ERROR", error.toException())
//            }
//        })
//    }
//}
//
//@Composable
//fun ItemList() {
//    val viewModel: FirebaseViewModel = FirebaseViewModel()
//    val items = viewModel.items.collectAsState()
//
//    LazyColumn {
////        Text(text = "List of Products")
//        items(items.value){
//          Text(text = "Title: ${it.title.toString()}")
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShopItTheme {

    }
}