package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopit.R
import com.example.shopit.ui.uiStates.CartViewUiState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartScreenViewModel,
    navController: NavController
) {
    var products = viewModel.cartViewUiState.collectAsState()
    val subTotal = viewModel.subTotal.collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "My Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                    }
                },
                modifier = Modifier
                    .height(40.dp)
            )
        }
    ) {
        if (products.value.isNotEmpty()) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(top = 52.dp, start = 8.dp, end = 8.dp)
                    .fillMaxSize()
            ) {
                items(products.value) { item ->
                    CartProductItem(
                        product = item,
                        onRemoveFromCart = {
                            scope.launch {
                                viewModel.removeProductFromCart(item)
                                viewModel.getProductsInCart()
                            }

                        }
                    )
                }

            }
            Text(text = "Subtotal: ${subTotal.value}")
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 52.dp)
            ){
                Text(text = "Cart is empty :(")
            }
        }

    }


}
val tempCartProduct = CartViewUiState(
    title = "Temp title",
    main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
    price = "12.0"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartProductItem(
    product: CartViewUiState,
    onRemoveFromCart:(product: CartViewUiState)->Unit
) {
    var quantity by rememberSaveable {
        mutableStateOf(product.quantity.toInt())
    }
    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.main_image,
                contentDescription = "Image",
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxHeight()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = product.title.toString())
                Text(text = "$${product.price}")
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            contentPadding = PaddingValues(0.dp),
                            onClick = { quantity++ },
                            modifier = Modifier
                                .width(45.dp)
                                .height(35.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase Quantity")
                        }
                        Text(
                            text = quantity.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 19.sp,
                            modifier = Modifier
                                .width(40.dp)
                        )
                        OutlinedButton(
                            contentPadding = PaddingValues(0.dp),
                            onClick = { quantity-- },
                            modifier = Modifier
                                .width(45.dp)
                                .height(35.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_minus_48___),
                                contentDescription = "Reduce Quantity",
                            )
                        }
                    }
                    Button(onClick = {
                        onRemoveFromCart(product)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_trash_48___),
                            contentDescription = "Remove From Cart",
                            modifier = Modifier
                        )

                    }


                }
            }

        }
    }
}


@Preview
@Composable
fun CartScreenPreview() {
    MaterialTheme {
//        CartScreen()
    }
}