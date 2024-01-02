package com.example.shopit.data.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observeConnection(): Flow<Status>
    enum class Status{
        Available, Unavailable, Lost, Losing
    }
}