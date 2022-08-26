package com.otakenne.devicehealthsdk.ui.composables.usersettings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import com.otakenne.devicehealthsdk.ui.viewmodels.UserSettingsViewModel

@Composable
internal fun UserSettingsComposable(viewModel: UserSettingsViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val batteryHealthThreshold = uiState.value.batteryHealthThreshold.toFloat()
    val globalRamUsageThreshold = uiState.value.globalRamUsageThreshold.toFloat()
    val systemCPULoadThreshold = uiState.value.systemCPULoadThreshold.toFloat()
    val shouldShowReverseNotification = uiState.value.shouldShowReverseNotification

    Column {
        SliderThresholdControl(value = batteryHealthThreshold, onValueChange = viewModel::setBatteryHealthThreshold)
        SliderThresholdControl(value = globalRamUsageThreshold, onValueChange = viewModel::setGlobalRamUsageThreshold)
        SliderThresholdControl(value = systemCPULoadThreshold, onValueChange = viewModel::setSystemCPULoadThreshold)
        SwitchControl(checked = shouldShowReverseNotification, onCheckedChange = viewModel::setShouldShowReverseNotification)
    }
}

@Composable
fun SliderThresholdControl(value: Float, onValueChange: (Float) -> (Unit)) {
    Slider(
        value = value,
        valueRange = 0f..100f,
        steps = 100,
        onValueChange = onValueChange
    )
}

@Composable
fun SwitchControl(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(checked = checked, onCheckedChange = onCheckedChange)
}