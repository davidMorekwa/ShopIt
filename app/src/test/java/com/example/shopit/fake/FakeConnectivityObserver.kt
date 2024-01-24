package com.example.shopit.fake

import com.example.shopit.data.network.connectionObserver.ConnectivityObserver

class FakeConnectivityObserver {
    fun getNetworkStatus(): ConnectivityObserver.Status{
        return ConnectivityObserver.Status.Available
    }
}