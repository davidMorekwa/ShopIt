package com.example.shopit.fake

import com.example.shopit.data.network.connectionObserver.ConnectivityObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeConnectivityObserver: ConnectivityObserver {

    override fun observe(): Flow<ConnectivityObserver.Status> = flow {
        emit(ConnectivityObserver.Status.Unavailable)
        delay(4000L)
        emit(ConnectivityObserver.Status.Unavailable)
    }
}