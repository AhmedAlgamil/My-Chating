package com.example.mychating.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class InternateStates {

    var myConnection : ConnectivityManager? = null

    fun isConnected(con: Context): Boolean {
        try {
            myConnection = con.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        } catch (ex: NullPointerException) {

        }
        @SuppressLint("MissingPermission") val activeNetwork = myConnection!!.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}