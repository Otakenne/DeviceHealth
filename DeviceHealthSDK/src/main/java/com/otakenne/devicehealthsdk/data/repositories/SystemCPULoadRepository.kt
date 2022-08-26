package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.datasources.ISystemCPULoadDataSource
import com.otakenne.devicehealthsdk.data.utility.Result
import javax.inject.Inject

internal class SystemCPULoadRepository (
    private val systemCPULoadDataSource: ISystemCPULoadDataSource
): ISystemCPULoadRepository {
    override fun getSystemCPULoad(): Result<Int> {
        return systemCPULoadDataSource.getSystemCPULoadDataSource()
    }
}