package com.example.shopit.data.network.connectionObserver

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class NetworkConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {
    private val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        println("Network is available")
                        send(ConnectivityObserver.Status.Available)
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        println("Network is Losing")
                        send(ConnectivityObserver.Status.Losing)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        println("Network is Lost")
                        send(ConnectivityObserver.Status.Lost)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        println("Network is Unavailable")
                        send(ConnectivityObserver.Status.Unavailable)
                    }
                }
            }
            connMgr.registerDefaultNetworkCallback(callback)
            awaitClose{
                connMgr.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}