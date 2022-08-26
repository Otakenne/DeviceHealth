package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import kotlinx.coroutines.flow.Flow

internal interface IHistoricalAlertsRepository {
    suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert)
    suspend fun getHistoricalAlerts(): Flow<List<HistoricalAlert>>
    suspend fun deleteAllHistoricalAlerts()
}