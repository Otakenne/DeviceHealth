package com.otakenne.devicehealthsdk.ui.composables.historicalalerts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import com.otakenne.devicehealthsdk.ui.viewmodels.HistoricalAlertsViewModel
import com.otakenne.devicehealthsdk.utility.notification.NotificationService
import kotlinx.coroutines.launch

@Composable
internal fun HistoricalAlertsComposable(
    navController: NavController,
    viewModel: HistoricalAlertsViewModel,
    deviceHealthMetricsViewModel: DeviceHealthMetricsViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    val historicalAlerts = uiState.value.historicalAlerts
    val coroutineScope = rememberCoroutineScope()
    val notificationService = NotificationService(LocalContext.current)

    coroutineScope.launch {
        notificationService.sendAlertNotifications(notificationService, deviceHealthMetricsViewModel)
    }

    Column {
        HistoricalAlertsScreenHeader(onClickBack = { navController.popBackStack() })
        when (uiState.value.initState) {
            HistoricalAlertsViewModel.InitState.LOADING -> {
                HistoricalAlertsLoader()
            }
            HistoricalAlertsViewModel.InitState.LOADED -> {
                if (historicalAlerts.isNotEmpty()) {
                    HistoricalAlertList(historicalAlerts = historicalAlerts)
                } else {
                    HistoricalAlertsError(
                        message = stringResource(R.string.historical_alerts_empty_message)
                    )
                }
            }
            HistoricalAlertsViewModel.InitState.ERROR -> {
                HistoricalAlertsError(
                    message = stringResource(R.string.historical_alerts_error)
                )
            }
        }
    }

}

@Composable
fun HistoricalAlertsScreenHeader(onClickBack: () -> (Unit)) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.history), fontWeight = FontWeight.ExtraLight, fontSize = 40.sp)
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
fun HistoricalAlertList(historicalAlerts: List<HistoricalAlert>) {
    Card(modifier = Modifier.padding(20.dp)) {
        LazyColumn {
            items(historicalAlerts) { historicalAlert ->
                HistoricalAlertRow(historicalAlert = historicalAlert)
            }
        }
    }
}

@Composable
fun HistoricalAlertRow(historicalAlert: HistoricalAlert) {
    Column(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
        Text("Alert Type: ${historicalAlert.historicalAlertType}")
        Text("Threshold: ${historicalAlert.threshold}")
        Text("Value: ${historicalAlert.value}")
    }
}

@Composable
fun HistoricalAlertsLoader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HistoricalAlertsError(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Text(
            text = message,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(30.dp),
            textAlign = TextAlign.Center
        )
    }
}