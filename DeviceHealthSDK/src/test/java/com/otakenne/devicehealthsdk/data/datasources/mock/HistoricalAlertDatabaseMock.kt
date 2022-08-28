package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class HistoricalAlertDatabaseMock: HistoricalAlert.Dao {
    private var historicalAlertsList: MutableList<HistoricalAlert> = mutableListOf()
    private var historicalAlerts: Flow<MutableList<HistoricalAlert>> = flow {
        historicalAlertsList
    }

    override suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert) {
        historicalAlertsList.add(historicalAlert)
        refreshFlow()
    }

    override fun getHistoricalAlerts(): Flow<List<HistoricalAlert>> {
        return historicalAlerts
    }

    override suspend fun deleteAllHistoricalAlerts() {
        historicalAlertsList.clear()
        refreshFlow()
    }

    private fun refreshFlow() {
        historicalAlerts = flow { historicalAlertsList }
    }
}