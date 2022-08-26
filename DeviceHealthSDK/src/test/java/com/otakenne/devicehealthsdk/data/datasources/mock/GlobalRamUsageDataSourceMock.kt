package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.datasources.IGlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.utility.Result

internal class GlobalRamUsageDataSourceMock(
    private val shouldFail: Boolean
): IGlobalRamUsageDataSource {
    override fun getGlobalRamUsage(): Result<Int> {
        return when (shouldFail) {
            true ->  {
                val globalRAMUsage = 40
                Result.Success(globalRAMUsage)
            }
            false -> Result.Error(Throwable("Failed to get RAM usage"))
        }
    }
}