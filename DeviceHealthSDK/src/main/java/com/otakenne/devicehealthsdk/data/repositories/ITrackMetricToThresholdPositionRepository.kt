package com.otakenne.devicehealthsdk.data.repositories

internal interface ITrackMetricToThresholdPositionRepository {
    fun setBatteryIsAboveThreshold(isAbove: Boolean)
    fun setGlobalRamIsAboveThreshold(isAbove: Boolean)
    fun setSystemCPULoadIsAboveThreshold(isAbove: Boolean)
    fun getBatteryIsAboveThreshold(): Boolean
    fun getGlobalRamIsAboveThreshold(): Boolean
    fun getSystemCPULoadIsAboveThreshold(): Boolean
    fun resetBatteryIsAboveThreshold()
    fun resetGlobalRamIsAboveThreshold()
    fun resetSystemCPULoadIsAboveThreshold()
}