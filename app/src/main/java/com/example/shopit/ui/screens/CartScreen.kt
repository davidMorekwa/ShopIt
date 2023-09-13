package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopit.R
import com.example.shopit.ui.uiStates.CartViewUiState
import kotlinx.coroutines.launch
import java.text.DecimalFormat

enum class PaymentMethods{
    MPESA,
    CARD,
    PAYPAL
}

@RequiresApi(Build.VERSION_CODES.O)
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
    val sheetState = rememberModalBottomSheetState()
    var isBottomModalSheetVisible: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var paymentChosen: String by rememberSaveable {
        mutableStateOf("")
    }
    var phoneNumber: String by rememberSaveable {
        mutableStateOf("")
    }
    var deliveryAddress: String by rememberSaveable {
        mutableStateOf("")
    }    
    val df = DecimalFormat("#.##")
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 42.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "SUBTOTAL: $${df.format(subTotal.value)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Button(
                    onClick = {
//                        navController.navigate(Screens.CHECKOUT_SCREEN.name)
                        isBottomModalSheetVisible = true
                    }
                ) {
                    Text(text = "Checkout")
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(top = 52.dp, start = 8.dp, end = 8.dp)
            ) {
                items(products.value) { item ->
                    CartProductItem(
                        product = item,
                        onRemoveFromCart = {
                            scope.launch {
                                viewModel.removeProductFromCart(item)
                                viewModel.getProductsInCart()
                            }

                        },
                        viewModel = viewModel
                    )
                }

            }
            AnimatedVisibility(visible = isBottomModalSheetVisible) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { isBottomModalSheetVisible = false },
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .height(720.dp)
                ) {
                    Column {
                        Text(text = "Delivery Address")
                        OutlinedTextField(
                            value = deliveryAddress,
                            onValueChange = { deliveryAddress = it },
                            placeholder = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.icons8_location_52___),
                                        contentDescription = "Location Icon"
                                    )
                                    Text(
                                        text = "Delivery Address",
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                imeAction = ImeAction.Next
                            ),

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {
                                    paymentChosen = PaymentMethods.MPESA.name
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (paymentChosen == PaymentMethods.MPESA.name) MaterialTheme.colorScheme.primary else Color.Transparent
                                ),
                                modifier = Modifier
                                    .height(45.dp)
                                    .width(75.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable._12px_m_pesa_logo_01_svg),
                                    contentDescription = "Mpesa Icon",
                                    modifier = Modifier
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    paymentChosen = PaymentMethods.CARD.name
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (paymentChosen == PaymentMethods.CARD.name) MaterialTheme.colorScheme.primary else Color.Transparent
                                ),
                                modifier = Modifier
                                    .height(45.dp)
                                    .width(75.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable._490135017_visa_82256),
                                    contentDescription = "Visa Icon",
                                    modifier = Modifier
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    paymentChosen = PaymentMethods.PAYPAL.name
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (paymentChosen == PaymentMethods.PAYPAL.name) MaterialTheme.colorScheme.primary else Color.Transparent
                                ),
                                modifier = Modifier
                                    .height(45.dp)
                                    .width(75.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.icons8_paypal_48___),
                                    contentDescription = "Paypal Icon",
                                    modifier = Modifier
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        AnimatedVisibility(visible = paymentChosen == PaymentMethods.MPESA.name) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = phoneNumber,
                                    onValueChange = { phoneNumber = it },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone,
                                        imeAction = ImeAction.Done
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "712345678",
                                            fontWeight = FontWeight.Light
                                        )
                                    },
                                    prefix = {
                                        Text(text = "+254")
                                    },
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                        .width(190.dp)
                                )
                                Button(
                                    onClick = {
                                        scope.launch {
                                            viewModel.getAccessToken()
                                        }
                                    }
                                ) {
                                    Text(
                                        text = "Complete"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
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
    viewModel: CartScreenViewModel,
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
                contentScale = ContentScale.Crop,
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
                            onClick = {
                                quantity++
//                                viewModel.addQuantity(product.id, quantity.toString())
                            },
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
//        CartScreen(
//            viewModel = viewModel(factory = viewModelProvider.factory),
//            navController = rememberNavController()
//        )
    }
}