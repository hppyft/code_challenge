package com.arctouch.codechallenge.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.arctouch.codechallenge.MoviesApplication

class NetworkUtil {

    companion object {
        fun isDeviceConnected(): Boolean {
            val cm = MoviesApplication.getInstance().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnected ?: false
        }
    }
}