package com.otakenne.devicehealthsdk.ui.composables.historicalalerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.ui.viewmodels.HistoricalAlertsViewModel

@Composable
internal fun HistoricalAlertsComposable(viewModel: HistoricalAlertsViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val historicalAlerts = uiState.value.historicalAlerts

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

@Composable
fun HistoricalAlertList(historicalAlerts: List<HistoricalAlert>) {
    LazyColumn {
        items(historicalAlerts) { historicalAlert ->
            HistoricalAlertRow(historicalAlert = historicalAlert)
        }
    }
}

@Composable
fun HistoricalAlertRow(historicalAlert: HistoricalAlert) {
    Column {
        Spacer(Modifier.width(8.dp))
        Text("Alert Type: ${historicalAlert.historicalAlertType}")
        Text("Threshold: ${historicalAlert.threshold}")
        Text("Value: ${historicalAlert.value}")
        Spacer(Modifier.width(8.dp))
    }
}

@Composable
fun HistoricalAlertsLoader() {
    CircularProgressIndicator()
}

@Composable
fun HistoricalAlertsError(message: String) {
    Text(text = message)
}