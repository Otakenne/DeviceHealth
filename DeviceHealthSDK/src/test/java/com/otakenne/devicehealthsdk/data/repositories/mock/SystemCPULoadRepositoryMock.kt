package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.ISystemCPULoadRepository
import com.otakenne.devicehealthsdk.data.utility.Result

internal class SystemCPULoadRepositoryMock(
    private val shouldFail: Boolean
): ISystemCPULoadRepository {
    override fun getSystemCPULoad(): Result<Int> {
        val systemCPULoad = 50
        return when (shouldFail) {
            true -> Result.Success(systemCPULoad)
            false -> Result.Error(Throwable("Failed to get system CPU load"))
        }
    }
}