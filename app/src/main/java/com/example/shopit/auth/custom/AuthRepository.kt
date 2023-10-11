package com.example.shopit.auth.custom

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(name: String, email: String, password: String): Flow<Resource<AuthResult>>
    fun logOut()
}