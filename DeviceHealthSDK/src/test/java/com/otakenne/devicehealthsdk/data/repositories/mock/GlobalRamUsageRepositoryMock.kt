package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.IGlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants

internal class GlobalRamUsageRepositoryMock(
    private val shouldNotFail: Boolean
): IGlobalRamUsageRepository {
    override fun getGlobalRamUsage(): Result<Int> {
        val globalRamUsage = Constants.DEFAULT_VALUE
        return when (shouldNotFail) {
            true -> Result.Success(globalRamUsage)
            false -> Result.Error(Throwable("Failed to get global ram usage"))
        }
    }
}