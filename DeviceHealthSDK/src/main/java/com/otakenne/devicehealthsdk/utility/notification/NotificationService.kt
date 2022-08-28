package com.otakenne.devicehealthsdk.utility.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Manages all notifications
 */
internal class NotificationService(
    private val context: Context
): INotificationService {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showThresholdAlertNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(context, THRESHOLD_ABOVE_ALERT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_warning_24)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .build()
        notificationManager.notify(1, notification)
    }

    override fun showNormalAlertNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(context, THRESHOLD_BELOW_ALERT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .build()
        notificationManager.notify(2, notification)
    }

    companion object {
        const val THRESHOLD_ABOVE_ALERT_CHANNEL_ID = "threshold_above_alert_channel"
        const val THRESHOLD_ABOVE_ALERT_CHANNEL_NAME = "Threshold Above Channel"
        const val THRESHOLD_BELOW_ALERT_CHANNEL_ID = "threshold_below_alert_channel"
        const val THRESHOLD_BELOW_ALERT_CHANNEL_NAME = "Threshold Below Channel"

        suspend fun sendAlertNotifications(notificationService: NotificationService, viewModel: DeviceHealthMetricsViewModel) {
            viewModel.notificationState.collectLatest {
                if (it.historicalAlertType != String.DEFAULT
                    && it.threshold != Int.DEFAULT
                    && it.value != Int.DEFAULT) {

                    val notificationTitle: String
                    val notificationText: String

                    if (it.value > it.threshold) {
                        notificationTitle = "Threshold alert!"
                        notificationText = "${it.historicalAlertType} has surpassed it's threshold value! Kill unused " +
                                "applications to conserve resources"
                        notificationService.showThresholdAlertNotification(notificationTitle, notificationText)
                    } else {
                        notificationTitle = "Back to normal"
                        notificationText = "${it.historicalAlertType} has returned below its threshold value. Your device's" +
                                "resources are now properly managed"
                        if (viewModel.uiState.value.shouldShowReverseNotification) {
                            notificationService.showNormalAlertNotification(
                                notificationTitle,
                                notificationText
                            )
                        }
                    }
                }
            }
        }
    }
}