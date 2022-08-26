package com.otakenne.devicehealthsdk.ui.composables.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import com.otakenne.devicehealthsdk.utility.navigation.Screen

@Composable
internal fun DeviceHealthComposable(navController: NavController, viewModel: DeviceHealthMetricsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Text("Battery Health")
        Text(uiState.batteryHealth.toString())
        Text("Global Ram")
        Text(uiState.globalRamUsage.toString())
        Text("System CPU Load")
        Text(uiState.systemCPULoad.toString())
        Button(
            onClick = {
                navController.navigate(route = Screen.UserSettingsScreen.route)
            }
        ) {
            Text("To user settings screen")
        }
        Button(
            onClick = {
                navController.navigate(route = Screen.HistoricalAlertsScreen.route)
            }
        ) {
            Text("To historical alert screen")
        }
    }
}