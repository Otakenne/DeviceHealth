package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.datasources.IBatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.utility.Result

internal class BatteryHealthDataSourceMock(
    private val shouldFail: Boolean
): IBatteryHealthDataSource {
    override fun getBatteryHealth(): Result<Int> {
        return when (shouldFail) {
            true ->  {
                val batteryHealth = 40
                Result.Success(batteryHealth)
            }
            false -> Result.Error(Throwable("Failed to get battery health"))
        }
    }
}