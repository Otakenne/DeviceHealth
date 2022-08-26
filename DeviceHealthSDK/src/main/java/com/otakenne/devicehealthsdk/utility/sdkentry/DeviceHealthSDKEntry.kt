package com.otakenne.devicehealthsdk.utility.sdkentry

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.otakenne.devicehealthsdk.di.appModules
import com.otakenne.devicehealthsdk.ui.activities.DeviceHealthActivity
import com.otakenne.devicehealthsdk.utility.notification.NotificationService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object DeviceHealthSDKEntry {

    fun initialize(context: Context) {
        createAboveNotificationChannel(context)
        createBelowNotificationChannel(context)
        startKoin {
            androidContext(context)
            modules(appModules)
        }
    }

    fun launchHomeActivity(context: Context) {
        val intent = Intent(context, DeviceHealthActivity::class.java)
        context.startActivity(intent)
    }

    private fun createAboveNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.THRESHOLD_ABOVE_ALERT_CHANNEL_ID,
                NotificationService.THRESHOLD_ABOVE_ALERT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Alerts you when your device metrics cross your predefined threshold values"

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createBelowNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.THRESHOLD_BELOW_ALERT_CHANNEL_ID,
                NotificationService.THRESHOLD_BELOW_ALERT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Alerts you when your device metrics return below your predefined threshold values"

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}