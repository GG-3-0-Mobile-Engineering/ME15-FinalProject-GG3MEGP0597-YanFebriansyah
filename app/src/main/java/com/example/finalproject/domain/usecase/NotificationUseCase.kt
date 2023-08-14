package com.example.finalproject.domain.usecase

import android.content.Context
import com.example.finalproject.data.notification.NotificationFloodDepth
import com.example.finalproject.presentation.model.Bencana
import javax.inject.Inject

class NotificationUseCase @Inject constructor() {
    suspend fun checkAndShowFloodAlertNotification(context: Context, bencana: List<Bencana>) {
        val notificationMessage = buildNotificationMessage(bencana)
        if (notificationMessage.isNotBlank()) {
            NotificationFloodDepth.showNotification(context, "Flood Alert", notificationMessage)
        }
    }

       suspend fun buildNotificationMessage(bencana: List<Bencana>): String {
        val filteredLocations = mutableListOf<String>()
        for (i in bencana) {
            val getDepth = i.floodDepth
            val message = i.codeArea
            if (getDepth != null && getDepth > 100) {
                filteredLocations.add(message ?: "")
            }
        }

        // Hapus duplikasi id daerah
        val distinctLocations = filteredLocations.distinct()
        return if (filteredLocations.isNotEmpty()) {
            "Berikut adalah ID daerah yang terdampak banjir dengan flood depth > 100: \n${
                distinctLocations.joinToString(",")
            },\"total ${filteredLocations.size} tempat dari ${distinctLocations.size} daerah\""
        } else {
            ""
        }
    }
}