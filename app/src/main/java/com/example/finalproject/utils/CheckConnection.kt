package com.example.finalproject.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object CheckConnection {
    fun showInternetMessage(context: Context) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("Retry") { dialog, _ ->
                dialog.dismiss()
                (context as Activity).recreate() // Reload activity to check internet connection again
            }
            .setNegativeButton("Exit") { _, _ ->
                (context as Activity).finish() // Close the app if user chooses to exit
            }
            .setCancelable(false)
            .create()
        alertDialog.show()
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Check for API level 23 or higher (Android Marshmallow or above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        } else {
            // For devices with API level below 23, use the deprecated method
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}





