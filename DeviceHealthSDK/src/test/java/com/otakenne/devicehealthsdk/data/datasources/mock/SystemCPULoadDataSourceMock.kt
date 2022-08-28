package com.otakenne.devicehealthsdk.data.datasources.mock

import com.otakenne.devicehealthsdk.data.datasources.ISystemCPULoadDataSource
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants

internal class SystemCPULoadDataSourceMock(
    private val shouldNotFail: Boolean
): ISystemCPULoadDataSource {
    override fun getSystemCPULoadDataSource(): Result<Int> {
        return when (shouldNotFail) {
            true ->  {
                val systemCPULoad = Constants.SYSTEM_CPU_LOAD
                Result.Success(systemCPULoad)
            }
            false -> Result.Error(Throwable(Constants.SYSTEM_CPU_LOAD_ERROR_MESSAGE))
        }
    }
}