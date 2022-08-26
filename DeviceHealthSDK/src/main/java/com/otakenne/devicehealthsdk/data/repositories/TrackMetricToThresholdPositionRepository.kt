package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.datasources.ITrackMetricToThresholdPositionDataSource
import javax.inject.Inject

internal class TrackMetricToThresholdPositionRepository (
    private val trackMetricToThresholdPositionDataSource: ITrackMetricToThresholdPositionDataSource
): ITrackMetricToThresholdPositionRepository {
    override fun setBatteryIsAboveThreshold(isAbove: Boolean) {
        trackMetricToThresholdPositionDataSource.setBatteryIsAboveThreshold(isAbove)
    }

    override fun setGlobalRamIsAboveThreshold(isAbove: Boolean) {
        trackMetricToThresholdPositionDataSource.setGlobalRamIsAboveThreshold(isAbove)
    }

    override fun setSystemCPULoadIsAboveThreshold(isAbove: Boolean) {
        trackMetricToThresholdPositionDataSource.setSystemCPULoadIsAboveThreshold(isAbove)
    }

    override fun getBatteryIsAboveThreshold(): Boolean {
        return trackMetricToThresholdPositionDataSource.getBatteryIsAboveThreshold()
    }

    override fun getGlobalRamIsAboveThreshold(): Boolean {
        return trackMetricToThresholdPositionDataSource.getGlobalRamIsAboveThreshold()
    }

    override fun getSystemCPULoadIsAboveThreshold(): Boolean {
        return trackMetricToThresholdPositionDataSource.getSystemCPULoadIsAboveThreshold()
    }

    override fun resetBatteryIsAboveThreshold() {
        trackMetricToThresholdPositionDataSource.resetBatteryIsAboveThreshold()
    }

    override fun resetGlobalRamIsAboveThreshold() {
        trackMetricToThresholdPositionDataSource.resetGlobalRamIsAboveThreshold()
    }

    override fun resetSystemCPULoadIsAboveThreshold() {
        trackMetricToThresholdPositionDataSource.resetSystemCPULoadIsAboveThreshold()
    }
}