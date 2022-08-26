package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.IGlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.utility.Result

internal class GlobalRamUsageRepositoryMock(
    private val shouldFail: Boolean
): IGlobalRamUsageRepository {
    override fun getGlobalRamUsage(): Result<Int> {
        val globalRamUsage = 50
        return when (shouldFail) {
            true -> Result.Success(globalRamUsage)
            false -> Result.Error(Throwable("Failed to get global ram usage"))
        }
    }
}