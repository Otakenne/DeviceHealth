package com.otakenne.devicehealthsdk.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import com.otakenne.devicehealthsdk.ui.theme.DeviceHealthTheme
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import com.otakenne.devicehealthsdk.utility.Constants
import com.otakenne.devicehealthsdk.utility.navigation.Navigation
import com.otakenne.devicehealthsdk.utility.notification.NotificationService
import com.otakenne.devicehealthsdk.utility.worker.GetMetricsWorker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

internal class DeviceHealthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workRequest = PeriodicWorkRequestBuilder<GetMetricsWorker>(
            Constants.WORK_PERIODICITY,
            TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()
        ).build()
        val workManager = WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                Constants.UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        val notificationService = NotificationService(applicationContext)

        setContent {
            DeviceHealthTheme {
//                sendAlertNotifications(notificationService, deviceHealthViewModel)
                Navigation()
            }
        }
    }

    private fun sendAlertNotifications(notificationService: NotificationService, viewModel: DeviceHealthMetricsViewModel) {
        lifecycleScope.launch {
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