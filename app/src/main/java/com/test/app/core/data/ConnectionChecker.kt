package com.test.app.core.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.test.app.core.di.CoreScope
import javax.inject.Inject

@CoreScope
class ConnectionChecker @Inject constructor(context: Context) {
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    var isConnected =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        } ?: false
        private set

    init {
        val networkRequest =
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
        connectivityManager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isConnected = true
            }

            override fun onUnavailable() {
                super.onUnavailable()
                isConnected = false
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isConnected = false
            }
        })
    }
}