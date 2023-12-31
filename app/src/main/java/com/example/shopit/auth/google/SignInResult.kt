package com.example.shopit.auth.google

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userEmail: String?,
    val profilePicture: String?
)
