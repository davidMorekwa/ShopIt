package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.viewmodels.AuthViewModel
import com.example.shopit.viewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onSuccessfullLogin: ()->Unit
) {
    var email: String by rememberSaveable {
        mutableStateOf("")
    }
    var password: String by rememberSaveable {
        mutableStateOf("")
    }
    var isShowPassword: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var isInvalidCredentials: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val state = authViewModel.signInState.collectAsState(initial = null)
    val context = LocalContext.current
    Scaffold(
        topBar = {
        },
        bottomBar = {

        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hey,",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "Welcome Back",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(text = "Email Address")
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Email, contentDescription = "email icon")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(text = "Password")
                        },
                        trailingIcon = {
                            Text(
                                text = if(isShowPassword)"Hide" else "Show",
                                modifier = Modifier
                                    .clickable {
                                        isShowPassword = !isShowPassword
                                        println("Clicked Show password")
                                    },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Lock, contentDescription = "password icon")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                scope.launch {
                                    authViewModel.loginUser(email, password)
                                }
                            }
                        ),
                        visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visible = isInvalidCredentials,
                        enter = slideInVertically(spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow))
                    ) {
                        Text(
                            text = "Invalid credentials",
                            fontSize = 15.sp,
                            color = Color.Red
                        )
                    }
                    Text(
                        text = "Forgot your Password?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                authViewModel.loginUser(email, password)
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp),
                            )
                    ) {
                        Text(text = "LogIn")
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Don't have an account? ")
                        Text(
                            text = "Register",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { navController.navigate(Screens.REGISTRATION_SCREEN.name) }
                        )
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isSuccess){
                scope.launch {
                    if(state.value?.isSuccess?.isNotEmpty() == true){
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                        println("SUCCESS MESSAGE $success")
                        onSuccessfullLogin()
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError){
                scope.launch {
                    if(state.value?.isError?.isNotEmpty() == true){
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                        println("Error MESSAGE $error")
//                        onSuccessfulSignIn()
                        isInvalidCredentials = true
                    }
                }
            }
        }

    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            authViewModel = viewModel(factory = viewModelProvider.factory),
            navController = rememberNavController(),
            onSuccessfullLogin = {}
        )
    }

}