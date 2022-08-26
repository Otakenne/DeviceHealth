package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CacheRepositoryMock: ICacheRepository {

    private var batteryHealthThreshold: Int = 50
    private var globalRamUsageThreshold: Int = 50
    private var systemCPULoadThreshold: Int = 50
    private var shouldShowReverseNotification: Boolean = false

    private var historicalAlertsList: MutableList<HistoricalAlert> = mutableListOf()
    private var historicalAlerts: Flow<MutableList<HistoricalAlert>> = flow {
        historicalAlertsList
    }

    override fun setBatteryHealthThreshold(threshold: Int) {
        batteryHealthThreshold = threshold
    }

    override fun setSystemCPULoadThreshold(threshold: Int) {
        systemCPULoadThreshold = threshold
    }

    override fun setGlobalRamUsageThreshold(threshold: Int) {
        globalRamUsageThreshold = threshold
    }

    override fun setShouldShowReverseNotification(shouldShow: Boolean) {
        shouldShowReverseNotification = shouldShow
    }

    override fun getBatteryHealthThreshold(): Int {
        return batteryHealthThreshold
    }

    override fun getSystemCPULoadThreshold(): Int {
        return systemCPULoadThreshold
    }

    override fun getGlobalRamUsageThreshold(): Int {
        return globalRamUsageThreshold
    }

    override fun getShouldShowReverseNotification(): Boolean {
        return shouldShowReverseNotification
    }

    override fun resetBatteryHealthThreshold() {
        batteryHealthThreshold = 50
    }

    override fun resetSystemCPULoadThreshold() {
        systemCPULoadThreshold = 50
    }

    override fun resetGlobalRamUsageThreshold() {
        globalRamUsageThreshold = 50
    }

    override fun resetShouldShowReverseNotification() {
        shouldShowReverseNotification = false
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