package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.IHistoricalAlertsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class HistoricalAlertsRepositoryMock: IHistoricalAlertsRepository {

    private var historicalAlertsList: MutableList<HistoricalAlert> = mutableListOf()
    private var historicalAlerts: Flow<MutableList<HistoricalAlert>> = flow {
        historicalAlertsList
    }

    override suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert) {
        historicalAlertsList.add(historicalAlert)
        refreshFlow()
    }

    override suspend fun getHistoricalAlerts(): Flow<List<HistoricalAlert>> {
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