package com.example.shopit.ui.screens
//
//import android.annotation.SuppressLint
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Button
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.shopit.R
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CheckoutScreen(
////    subtotal: State<Double>,
//    navController: NavController
//) {
//    val subtotal = 120.3
//    var deliveryAddress: String by rememberSaveable {
//        mutableStateOf("")
//    }
//    var radioButtonSelected: Int by rememberSaveable {
//        mutableStateOf(0)
//    }
//    var phoneNumber: String by rememberSaveable {
//        mutableStateOf("")
//    }
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text(text = "Checkout") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                modifier = Modifier
//                    .border(1.dp, Color.Red)
//                    .height(40.dp)
//            )
//        }
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .border(1.dp, Color.Red)
//                .padding(top = 55.dp, end = 8.dp, start = 8.dp)
//        ){
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
//                Text(text = "Delivery Address")
//                OutlinedTextField(
//                    value = deliveryAddress,
//                    onValueChange = { deliveryAddress = it },
//                    placeholder = {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(10.dp)
//                        ) {
//                            Icon(painterResource(id = R.drawable.icons8_location_52___), contentDescription = "Location Icon")
//                            Text(text = "Delivery Address")
//                        }
//                    },
//                    shape = RoundedCornerShape(20.dp),
//                    keyboardOptions = KeyboardOptions(
//                        capitalization = KeyboardCapitalization.Words,
//                        imeAction = ImeAction.Next
//                    ),
//                )
//                Text(text = "Payment Method")
//                Column {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        RadioButton(
//                            selected = radioButtonSelected == 1,
//                            onClick = { radioButtonSelected = 1 }
//                        )
//                        Text(text = "Mpesa")
//
//                    }
//                    AnimatedVisibility(visible = radioButtonSelected == 1) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            OutlinedTextField(
//                                value = phoneNumber,
//                                onValueChange = {phoneNumber = it},
//                                keyboardOptions = KeyboardOptions(
//                                    keyboardType = KeyboardType.Phone,
//                                    imeAction = ImeAction.Next
//                                ),
//                                placeholder = {
//                                    Text(text = "e.g. 254712345678")
//                                },
//                                shape = RoundedCornerShape(20.dp),
//                                modifier = Modifier
//                                    .width(190.dp)
//                            )
//                            Button(onClick = { /*TODO*/ }) {
//                                Text(text = "Complete")
//                            }
//                        }
//
//                    }
//
//                }
//                Column {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        RadioButton(
//                            selected = radioButtonSelected == 2,
//                            onClick = { radioButtonSelected = 2 }
//                        )
//                        Text(text = "Debit/Credit Card")
//                        Image(
//
//                            contentDescription = "Visa Payment",
//                            modifier = Modifier
//                                .size(54.dp)
//                        )
//                    }
//                    AnimatedVisibility(visible = radioButtonSelected == 2) {
//                        Row {
//                            Text(text = "This is payment by card")
//                        }
//                    }
//                }
//                Column {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        RadioButton(
//                            selected = radioButtonSelected == 3,
//                            onClick = { radioButtonSelected = 3 }
//                        )
//                        Text(text = "PayPal")
//                        Image(
//
//                            contentDescription = "PayPal Payment",
//                            modifier = Modifier
//                                .size(54.dp)
//                        )
//                    }
//                }
//            }
//
////            Text(text = "subtotal: $subtotal")
//        }
//
//    }
//}
//
//
//@Preview
//@Composable
//fun CheckoutScreenPreview() {
//    MaterialTheme {
//        CheckoutScreen(
//            navController = rememberNavController()
//        )
//    }
//}