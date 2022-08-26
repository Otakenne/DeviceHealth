package com.otakenne.devicehealthsdk.utility.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.otakenne.devicehealthsdk.R

class NotificationService(
    private val context: Context
): INotificationService {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showThresholdAlertNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(context, THRESHOLD_ABOVE_ALERT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_warning_24)
            .setContentTitle(title)
            .setContentText(text)
            .build()
        notificationManager.notify(1, notification)
    }

    override fun showNormalAlertNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(context, THRESHOLD_BELOW_ALERT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            .setContentTitle(title)
            .setContentText(text)
            .build()
        notificationManager.notify(2, notification)
    }

    companion object {
        const val THRESHOLD_ABOVE_ALERT_CHANNEL_ID = "threshold_above_alert_channel"
        const val THRESHOLD_ABOVE_ALERT_CHANNEL_NAME = "Threshold Above Channel"
        const val THRESHOLD_BELOW_ALERT_CHANNEL_ID = "threshold_below_alert_channel"
        const val THRESHOLD_BELOW_ALERT_CHANNEL_NAME = "Threshold Below Channel"
    }
}