package com.soomapps.universalremotecontrol.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectionDetector {
    companion object {
        @JvmStatic
        fun checkInternetConnection(context: Context): Boolean {
            val con_manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (con_manager.activeNetworkInfo != null
                    && con_manager.activeNetworkInfo!!.isAvailable
                    && con_manager.activeNetworkInfo!!.isConnected) {
                true
            } else {
                false
            }
        }
    }
}