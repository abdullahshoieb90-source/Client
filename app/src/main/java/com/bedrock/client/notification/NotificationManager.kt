
package com.bedrock.client.notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bedrock.client.R

class ClientNotificationManager(private val context: Context) {
    private val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

    init { createChannel() }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("client_channel", "Bedrock Client", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(channel)
        }
    }

    fun show(title: String, content: String) {
        val notif = NotificationCompat.Builder(context, "client_channel")
            .setContentTitle(title).setContentText(content).setSmallIcon(R.drawable.ic_launcher).build()
        nm.notify(1, notif)
    }
}
