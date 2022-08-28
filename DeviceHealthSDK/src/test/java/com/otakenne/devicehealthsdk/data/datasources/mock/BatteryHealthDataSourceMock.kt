package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.datasources.IBatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants

internal class BatteryHealthDataSourceMock(
    private val shouldNotFail: Boolean
): IBatteryHealthDataSource {
    override fun getBatteryHealth(): Result<Int> {
        return when (shouldNotFail) {
            true ->  {
                val batteryHealth = Constants.BATTERY_HEALTH
                Result.Success(batteryHealth)
            }
            false -> Result.Error(Throwable(Constants.BATTERY_HEALTH_ERROR_MESSAGE))
        }
    }
}