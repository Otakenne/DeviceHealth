package com.otakenne.devicehealthsdk.ui.composables.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import com.otakenne.devicehealthsdk.utility.navigation.Screen
import com.otakenne.devicehealthsdk.utility.notification.NotificationService
import kotlinx.coroutines.launch

@Composable
internal fun DeviceHealthComposable(navController: NavController, viewModel: DeviceHealthMetricsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val notificationService = NotificationService(LocalContext.current)

    coroutineScope.launch {
        notificationService.sendAlertNotifications(notificationService, viewModel)
    }

    Column {
        DeviceMetricsScreenHeader(
            onClickSettings = { navController.navigate(route = Screen.UserSettingsScreen.route) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        DeviceSingleMetricCard(
            stringResource(R.string.battery_health),
            uiState.batteryHealth
        )
        DeviceSingleMetricCard(
            stringResource(R.string.global_ram),
            uiState.globalRamUsage
        )
        DeviceSingleMetricCard(
            stringResource(R.string.system_cpu_load),
            uiState.systemCPULoad
        )
        Spacer(modifier = Modifier.height(20.dp))
        HistoricalAlertsButton(onClick = { navController.navigate(route = Screen.HistoricalAlertsScreen.route) })
    }
}

@Composable
fun DeviceMetricsScreenHeader(onClickSettings: () -> (Unit)) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.metrics), fontWeight = FontWeight.Thin, fontSize = 40.sp)
        IconButton(
            modifier = Modifier.then(Modifier.size(24.dp)),
            onClick = onClickSettings
        ) {
            Icon(
                Icons.Outlined.Settings,
                stringResource(R.string.settings_button),
                tint = Color.Black)
        }
    }
}

@Composable
fun DeviceSingleMetricCard(metric: String, value: Int) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(metric, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(stringResource(R.string.percent, value.toString()), fontSize = 17.sp)
            }
            LinearProgressIndicator(
                progress = (value.toFloat() / 100),
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
    }
}

@Composable
fun HistoricalAlertsButton(onClick: () -> (Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(2.dp, Color.Black),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
        ) {
            Text(text = stringResource(R.string.historical_alerts))
        }
    }
}