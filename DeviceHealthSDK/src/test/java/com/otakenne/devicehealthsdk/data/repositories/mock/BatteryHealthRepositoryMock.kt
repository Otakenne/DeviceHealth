package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.IBatteryHealthRepository
import com.otakenne.devicehealthsdk.data.utility.Result

internal class BatteryHealthRepositoryMock(
    private val shouldFail: Boolean
): IBatteryHealthRepository {
    override fun getBatteryHealth(): Result<Int> {
        val batteryHealth = 50
        return when (shouldFail) {
            true -> Result.Success(batteryHealth)
            false -> Result.Error(Throwable("Failed to get battery health"))
        }
    }
}