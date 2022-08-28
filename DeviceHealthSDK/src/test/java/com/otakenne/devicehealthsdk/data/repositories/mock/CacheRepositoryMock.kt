package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import com.otakenne.devicehealthsdk.utility.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

internal class CacheRepositoryMock: ICacheRepository {

    private var batteryHealthThreshold: Int = Constants.DEFAULT_VALUE
    private var globalRamUsageThreshold: Int = Constants.DEFAULT_VALUE
    private var systemCPULoadThreshold: Int = Constants.DEFAULT_VALUE
    private var shouldShowReverseNotification: Boolean = false

    private val fakeFlow = MutableSharedFlow<MutableList<HistoricalAlert>>()
    suspend fun emit(value: MutableList<HistoricalAlert>) = fakeFlow.emit(value)
    var historicalAlertsList: MutableList<HistoricalAlert> = mutableListOf()
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
        batteryHealthThreshold = Constants.DEFAULT_VALUE
    }

    override fun resetSystemCPULoadThreshold() {
        systemCPULoadThreshold = Constants.DEFAULT_VALUE
    }

    override fun resetGlobalRamUsageThreshold() {
        globalRamUsageThreshold = Constants.DEFAULT_VALUE
    }

    override fun resetShouldShowReverseNotification() {
        shouldShowReverseNotification = false
    }

    override suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert) {
        historicalAlertsList.add(historicalAlert)
        refreshFlow()
    }

    override suspend fun getHistoricalAlerts() = fakeFlow

    override suspend fun deleteAllHistoricalAlerts() {
        historicalAlertsList.clear()
        refreshFlow()
    }

    private fun refreshFlow() {

        historicalAlerts = flow { historicalAlertsList }
    }
}