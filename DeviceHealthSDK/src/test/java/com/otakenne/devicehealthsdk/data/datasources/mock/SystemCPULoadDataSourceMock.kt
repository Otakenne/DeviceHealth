package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.datasources.ISystemCPULoadDataSource
import com.otakenne.devicehealthsdk.data.utility.Result

internal class SystemCPULoadDataSourceMock(
    private val shouldFail: Boolean
): ISystemCPULoadDataSource {
    override fun getSystemCPULoadDataSource(): Result<Int> {
        return when (shouldFail) {
            true ->  {
                val systemCPULoad = 60
                Result.Success(systemCPULoad)
            }
            false -> Result.Error(Throwable("Failed to get system CPU load"))
        }
    }
}