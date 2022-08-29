package com.otakenne.devicehealthsdk.utility.sdkentry

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.di.appModules
import com.otakenne.devicehealthsdk.ui.activities.DeviceHealthActivity
import com.otakenne.devicehealthsdk.utility.Constants
import com.otakenne.devicehealthsdk.utility.notification.NotificationService
import com.otakenne.devicehealthsdk.utility.worker.GetMetricsWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

/**
 * Entry point into the SDK. Also creates notification channels for Android O+
 */
object DeviceHealthSDKEntry {

    val workRequest = PeriodicWorkRequestBuilder<GetMetricsWorker>(
        Constants.WORK_PERIODICITY,
        TimeUnit.MINUTES
    ).setConstraints(
        Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
    ).build()

    /**
     * This method initializes the SDK. Call this method in the application class onCreate method
     * @param context: Application context
     */
    fun initialize(context: Context) {
        createAboveNotificationChannel(context)
        createBelowNotificationChannel(context)
        startKoin {
            androidContext(context)
            modules(appModules)
        }
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                Constants.UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

    /**
     * This is the entry point to the SDK, call this method from your activity
     * @param context: Activity context
     */
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
            channel.description = context.getString(R.string.above_threshold_channel_description)

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
            channel.description = context.getString(R.string.below_threshold_channel_description)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}