package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class HistoricalAlertsRepository (
    private val cacheRepository: ICacheRepository
): IHistoricalAlertsRepository {
    override suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert) {
        cacheRepository.insertHistoricalAlert(historicalAlert)
    }

    override suspend fun getHistoricalAlerts(): Flow<List<HistoricalAlert>> {
        return cacheRepository.getHistoricalAlerts()
    }

    override suspend fun deleteAllHistoricalAlerts() {
        cacheRepository.deleteAllHistoricalAlerts()
    }

}