package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.datasources.IGlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants

internal class GlobalRamUsageDataSourceMock(
    private val shouldNotFail: Boolean
): IGlobalRamUsageDataSource {
    override fun getGlobalRamUsage(): Result<Int> {
        return when (shouldNotFail) {
            true ->  {
                val globalRAMUsage = Constants.GLOBAL_RAM_USAGE
                Result.Success(globalRAMUsage)
            }
            false -> Result.Error(Throwable(Constants.GLOBAL_RAM_USAGE_ERROR_MESSAGE))
        }
    }
}