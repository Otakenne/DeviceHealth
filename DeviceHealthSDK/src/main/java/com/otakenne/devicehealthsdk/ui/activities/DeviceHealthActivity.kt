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
import com.otakenne.devicehealthsdk.utility.sdkentry.DeviceHealthSDKEntry.workRequest
import com.otakenne.devicehealthsdk.utility.worker.GetMetricsWorker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

internal class DeviceHealthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeviceHealthTheme {
                Navigation()
            }
        }
    }
}