package com.example.mvvmishapp.main.custom.managers

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity

object SystemServices {
    fun getNetworkService(context: Context): NetworkService {
        val connectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        return NetworkService(connectivityManager)
    }
}

class NetworkService(private val connectivityManager: ConnectivityManager) {

    fun checkNetworkConnection(callback: (Boolean) -> Unit) {
        val inNetworkConnected = fun(): Boolean {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

        callback.invoke(inNetworkConnected())

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                callback.invoke(true)
            }

            override fun onLost(network: Network) {
                callback.invoke(false)
            }
        })
    }
}