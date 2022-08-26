package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.datasources.IBatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.utility.Result
import javax.inject.Inject

internal class BatteryHealthRepository (
    private val batteryHealthDataSource: IBatteryHealthDataSource
): IBatteryHealthRepository {
    override fun getBatteryHealth(): Result<Int> {
        return batteryHealthDataSource.getBatteryHealth()
    }
}