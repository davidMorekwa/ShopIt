package com.example.shopit.ui.screens.authscreens

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.screens.Screens
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    onSuccessfulRegistration:()->Unit
) {
    var name: String by rememberSaveable {
        mutableStateOf("")
    }
    var email: String by rememberSaveable {
        mutableStateOf("")
    }
    var password: String by rememberSaveable {
        mutableStateOf("")
    }
    var confirm_password: String by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordMatch: Boolean by rememberSaveable {
        mutableStateOf(true)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isShowPassword: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val state = authViewModel.registerState.collectAsState(initial = null)
    val context = LocalContext.current
    Scaffold(
        topBar = {
        },
        bottomBar = {

        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Hey,",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "Create an Account",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        label = {
                            Text(text = "Name")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(Icons.Filled.Person, contentDescription = "User icon")
                        },
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        label = {
                            Text(text = "Email")
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Email, contentDescription = "Email icon")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {password = it},
                        label = {
                            Text(text = "Password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(Icons.Filled.Lock, contentDescription = "Password Icon")
                        },
                        trailingIcon = {
                            Text(
                                text = if(isShowPassword)"Hide" else "Show",
                                modifier = Modifier
                                    .clickable {
                                        isShowPassword = !isShowPassword
                                    },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        },
                        visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = confirm_password,
                        onValueChange = {confirm_password = it},
                        label = {
                            Text(text = "Confirm Password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        leadingIcon = {
                            Icon(Icons.Filled.Lock, contentDescription = "Password Icon")
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                isPasswordMatch = password == confirm_password
                                keyboardController?.hide()
                                authViewModel.registerUser(email, password, name)
                            }
                        ),
                        trailingIcon = {
                            Text(
                                text = if(isShowPassword)"Hide" else "Show",
                                modifier = Modifier
                                    .clickable {
                                        isShowPassword = !isShowPassword
                                    },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        },
                        visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    AnimatedVisibility(
                        visible = !isPasswordMatch,
                        enter = slideInVertically(spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow))
                    ) {
                        Text(
                            text = "Passwords don't match!",
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    }

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
                            println("EMAIL: $email, PASSWORD: $password")
                            authViewModel.registerUser(email = email, password = password, name = name)
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .shadow(10.dp, RoundedCornerShape(20.dp))
                    ) {
                        Text(text = "Register")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Already have an account? ")
                        Text(
                            text = "Login",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screens.LOGIN_SCREEN.name)
                                }
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
                        onSuccessfulRegistration()
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError){
                scope.launch {
                    if(state.value?.isError?.isNotEmpty() == true){
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                        println("Error MESSAGE $error")
                    }
                }
            }
        }

    }


}

@Preview
@Composable
fun RegistrationPreview() {
    MaterialTheme {
        RegistrationScreen(
            authViewModel = viewModel(),
            navController = rememberNavController(),
            onSuccessfulRegistration = {}
        )
    }
}