package com.example.finalproject.data.notification

import android.content.Context

object NotificationFloodDepth {
     fun showNotification(context: Context, title: String, message: String) {
         val notificationManager = NotificationManager(context)
         notificationManager.showBigTextNotification(title, message)
     }
}
