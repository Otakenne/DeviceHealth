package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.ITrackMetricToThresholdPositionRepository

internal class TrackMetricToThresholdPositionRepositoryMock: ITrackMetricToThresholdPositionRepository {
    
    private var batteryHealthIsAbove: Boolean = false
    private var globalRamUsageIsAbove: Boolean = false
    private var systemCPULoadIsAbove: Boolean = false

    override fun setBatteryIsAboveThreshold(isAbove: Boolean) {
        batteryHealthIsAbove = isAbove
    }

    override fun setGlobalRamIsAboveThreshold(isAbove: Boolean) {
        globalRamUsageIsAbove = isAbove
    }

    override fun setSystemCPULoadIsAboveThreshold(isAbove: Boolean) {
        systemCPULoadIsAbove = isAbove
    }

    override fun getBatteryIsAboveThreshold(): Boolean {
        return batteryHealthIsAbove
    }

    override fun getGlobalRamIsAboveThreshold(): Boolean {
        return globalRamUsageIsAbove
    }

    override fun getSystemCPULoadIsAboveThreshold(): Boolean {
        return systemCPULoadIsAbove
    }

    override fun resetBatteryIsAboveThreshold() {
        batteryHealthIsAbove = false
    }

    override fun resetGlobalRamIsAboveThreshold() {
        globalRamUsageIsAbove = false
    }

    override fun resetSystemCPULoadIsAboveThreshold() {
        systemCPULoadIsAbove = false
    }
}