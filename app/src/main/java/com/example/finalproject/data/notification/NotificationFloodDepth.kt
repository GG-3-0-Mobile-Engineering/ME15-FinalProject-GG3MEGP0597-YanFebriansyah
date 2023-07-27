package com.example.finalproject.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.finalproject.R

 object NotificationFloodDepth {
     fun showNotification(context: Context, title: String, message: String) {
         val notificationManager = NotificationManager(context)
         notificationManager.showBigTextNotification(title, message)
     }
}
