package com.example.finalproject.domain.usecase

import android.content.Context
import com.example.finalproject.presentation.model.Bencana
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NotificationUseCaseTest {

    private val context = mock<Context>()
    private val bencana = listOf(
        Bencana(codeArea = "12345678", floodDepth = 120),
        Bencana(codeArea = "98765432", floodDepth = 150),
    )

    @Test
    fun `should show notification when there are flooded areas with flood depth more than 100`() =
        runBlocking {
            // Arrange
            val notificationUseCase = mockk<NotificationUseCase>()
            val notificationMessage =
                "Berikut adalah ID daerah yang terdampak banjir dengan flood depth > 100:\n" +
                        "12345678, 98765432, total 2 tempat dari 2 daerah"
            whenever(notificationUseCase.buildNotificationMessage(bencana)).thenReturn(
                notificationMessage
            )

            // Act
            notificationUseCase.checkAndShowFloodAlertNotification(context, bencana)

        }
}