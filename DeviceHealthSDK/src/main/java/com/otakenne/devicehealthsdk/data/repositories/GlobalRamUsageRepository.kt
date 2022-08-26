package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.datasources.IGlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.utility.Result
import javax.inject.Inject

internal class GlobalRamUsageRepository (
    private val globalRamUsageDataSource: IGlobalRamUsageDataSource
): IGlobalRamUsageRepository {
    override fun getGlobalRamUsage(): Result<Int> {
        return globalRamUsageDataSource.getGlobalRamUsage()
    }
}