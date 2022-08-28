package com.otakenne.devicehealthsdk.ui.composables.usersettings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.ui.viewmodels.UserSettingsViewModel

@Composable
internal fun UserSettingsComposable(navController: NavController, viewModel: UserSettingsViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val batteryHealthThreshold = uiState.value.batteryHealthThreshold.toFloat()
    val globalRamUsageThreshold = uiState.value.globalRamUsageThreshold.toFloat()
    val systemCPULoadThreshold = uiState.value.systemCPULoadThreshold.toFloat()
    val shouldShowReverseNotification = uiState.value.shouldShowReverseNotification

    Column {
        UserSettingsScreenHeader(onClickBack = { navController.popBackStack() })
        SliderThresholdControl(
            title = stringResource(R.string.battery_level_threshold),
            value = batteryHealthThreshold,
            onValueChange = viewModel::setBatteryHealthThreshold
        )
        SliderThresholdControl(
            title = stringResource(R.string.global_ram_usage_threshold),
            value = globalRamUsageThreshold,
            onValueChange = viewModel::setGlobalRamUsageThreshold
        )
        SliderThresholdControl(
            title = stringResource(R.string.system_cpu_load_threshold),
            value = systemCPULoadThreshold,
            onValueChange = viewModel::setSystemCPULoadThreshold
        )
        SwitchControl(
            title = stringResource(R.string.show_reverse_notification),
            checked = shouldShowReverseNotification,
            onCheckedChange = viewModel::setShouldShowReverseNotification
        )
    }
}

@Composable
fun UserSettingsScreenHeader(onClickBack: () -> (Unit)) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.settings), fontWeight = FontWeight.ExtraLight, fontSize = 40.sp)
        IconButton(
            modifier = Modifier.then(Modifier.size(24.dp)),
            onClick = onClickBack
        ) {
            Icon(
                Icons.Outlined.Close,
                stringResource(R.string.back_button),
                tint = Color.Black)
        }
    }
}

@Composable
fun SliderThresholdControl(title: String, value: Float, onValueChange: (Float) -> (Unit)) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
                Text(text = stringResource(R.string.percent, value.toInt().toString()), modifier = Modifier.padding(10.dp))
            }
            Divider(modifier = Modifier.padding(horizontal = 10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(stringResource(R.string.zero), modifier = Modifier.padding(10.dp), color = Color.DarkGray)
                Slider(
                    value = value,
                    valueRange = 0f..100f,
                    steps = 100,
                    onValueChange = onValueChange,
                    modifier = Modifier.weight(1f)
                )
                Text(stringResource(R.string.hundred), modifier = Modifier.padding(10.dp), color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun SwitchControl(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Column() {
            Text(
                title,
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
            Divider(modifier = Modifier.padding(horizontal = 10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.enable_reverse_alerts))
                Switch(checked = checked, onCheckedChange = onCheckedChange)
            }
        }
    }
}