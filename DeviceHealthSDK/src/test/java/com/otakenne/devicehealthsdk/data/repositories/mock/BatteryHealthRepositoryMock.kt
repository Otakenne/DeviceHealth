package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.IBatteryHealthRepository
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants

internal class BatteryHealthRepositoryMock(
    private val shouldNotFail: Boolean
): IBatteryHealthRepository {
    override fun getBatteryHealth(): Result<Int> {
        val batteryHealth = Constants.DEFAULT_VALUE
        return when (shouldNotFail) {
            true -> Result.Success(batteryHealth)
            false -> Result.Error(Throwable("Failed to get battery health"))
        }
    }
}