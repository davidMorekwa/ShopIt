package com.example.shopit.ui.screens.authscreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopit.auth.custom.AuthRepository
import com.example.shopit.auth.custom.Resource
import com.example.shopit.auth.custom.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {
    val _signInState = Channel<SignInState>()
    var signInState = _signInState.receiveAsFlow()
    val _registerState = Channel<SignInState>()
    var registerState = _registerState.receiveAsFlow()
    fun registerUser(email: String, password: String, name: String){
        viewModelScope.launch {
            repository.registerUser(name = name, email = email, password = password).collect{result ->
                when (result){
                    is Resource.Success -> {
                        _registerState.send(SignInState(isSuccess = "Register Successful"))
                    }
                    is Resource.Loading -> {
                        _registerState.send(SignInState(isLoading = true))
                    }
                    is Resource.Error  -> {
                        _registerState.send(SignInState(isError = result.message))
                        println("REGISTRATION ERROR")
                        println(result)
                    }

                    else -> {null}
                }
            }
        }
    }
    fun logOut(){
        viewModelScope.launch {
            repository.logOut()
        }
    }
    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            repository.loginUser(email, password).collect{result ->
                when (result){
                    is Resource.Success -> {
                        _signInState.send(SignInState(isSuccess = "Sign In Successful"))
                    }
                    is Resource.Loading -> {
                        _signInState.send(SignInState(isLoading = true))
                    }
                    is Resource.Error  -> {
                        _signInState.send(SignInState(isError = result.message))
                        println("SIGNIN ERROR!! ${result.message}")
                    }

                    else -> {null}
                }
            }
        }
    }
}