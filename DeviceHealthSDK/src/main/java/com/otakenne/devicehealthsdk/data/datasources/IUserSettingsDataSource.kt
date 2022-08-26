package com.otakenne.devicehealthsdk.data.datasources

internal interface IUserSettingsDataSource {
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
}