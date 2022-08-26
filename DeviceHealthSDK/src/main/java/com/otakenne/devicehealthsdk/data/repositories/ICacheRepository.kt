package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.utility.Result
import kotlinx.coroutines.flow.Flow

internal interface ICacheRepository {
    fun setBatteryHealthThreshold(threshold: Int)
    fun setSystemCPULoadThreshold(threshold: Int)
    fun setGlobalRamUsageThreshold(threshold: Int)
    fun setShouldShowReverseNotification(shouldShow: Boolean)
    fun getBatteryHealthThreshold(): Int
    fun getSystemCPULoadThreshold(): Int
    fun getGlobalRamUsageThreshold(): Int
    fun getShouldShowReverseNotification(): Boolean
    fun resetBatteryHealthThreshold()
    fun resetSystemCPULoadThreshold()
    fun resetGlobalRamUsageThreshold()
    fun resetShouldShowReverseNotification()
    suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert)
    suspend fun getHistoricalAlerts(): Flow<List<HistoricalAlert>>
    suspend fun deleteAllHistoricalAlerts()
}