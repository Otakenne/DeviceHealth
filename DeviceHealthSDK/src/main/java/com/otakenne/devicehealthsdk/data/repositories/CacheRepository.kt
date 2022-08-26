package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.datasources.IUserSettingsDataSource
import com.otakenne.devicehealthsdk.data.datasources.room.HistoricalAlertsDatabase
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.utility.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class CacheRepository (
    private val historicalAlertsDatabase: HistoricalAlertsDatabase,
    private val userSettingsDataSource: IUserSettingsDataSource
): ICacheRepository {
    override fun setBatteryHealthThreshold(threshold: Int) {
        userSettingsDataSource.setBatteryHealthThreshold(threshold)
    }

    override fun setSystemCPULoadThreshold(threshold: Int) {
        userSettingsDataSource.setSystemCPULoadThreshold(threshold)
    }

    override fun setGlobalRamUsageThreshold(threshold: Int) {
        userSettingsDataSource.setGlobalRamUsageThreshold(threshold)
    }

    override fun setShouldShowReverseNotification(shouldShow: Boolean) {
        userSettingsDataSource.setShouldShowReverseNotification(shouldShow)
    }

    override fun getBatteryHealthThreshold(): Int {
        return userSettingsDataSource.getBatteryHealthThreshold()
    }

    override fun getSystemCPULoadThreshold(): Int {
        return userSettingsDataSource.getSystemCPULoadThreshold()
    }

    override fun getGlobalRamUsageThreshold(): Int {
        return userSettingsDataSource.getGlobalRamUsageThreshold()
    }

    override fun getShouldShowReverseNotification(): Boolean {
        return userSettingsDataSource.getShouldShowReverseNotification()
    }

    override fun resetBatteryHealthThreshold() {
        userSettingsDataSource.resetBatteryHealthThreshold()
    }

    override fun resetSystemCPULoadThreshold() {
        userSettingsDataSource.resetSystemCPULoadThreshold()
    }

    override fun resetGlobalRamUsageThreshold() {
        userSettingsDataSource.resetGlobalRamUsageThreshold()
    }

    override fun resetShouldShowReverseNotification() {
        userSettingsDataSource.resetShouldShowReverseNotification()
    }

    override suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert) {
        historicalAlertsDatabase.historicalAlertsDao()
            .insertHistoricalAlert(historicalAlert)
    }

    override suspend fun getHistoricalAlerts(): Flow<List<HistoricalAlert>> {
        return historicalAlertsDatabase.historicalAlertsDao()
            .getHistoricalAlerts()
    }

    override suspend fun deleteAllHistoricalAlerts() {
        historicalAlertsDatabase.historicalAlertsDao()
            .deleteAllHistoricalAlerts()
    }
}