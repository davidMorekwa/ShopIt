package com.example.shopit.auth.custom

data class SignInState(
    val isLoading:Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)
