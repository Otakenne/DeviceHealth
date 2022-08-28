package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.ISystemCPULoadRepository
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants

internal class SystemCPULoadRepositoryMock(
    private val shouldNotFail: Boolean
): ISystemCPULoadRepository {
    override fun getSystemCPULoad(): Result<Int> {
        val systemCPULoad = Constants.DEFAULT_VALUE
        return when (shouldNotFail) {
            true -> Result.Success(systemCPULoad)
            false -> Result.Error(Throwable("Failed to get system CPU load"))
        }
    }
}