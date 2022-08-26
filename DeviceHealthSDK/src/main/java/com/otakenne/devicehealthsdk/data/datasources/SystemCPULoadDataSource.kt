package com.otakenne.devicehealthsdk.data.datasources

import com.otakenne.devicehealthsdk.data.utility.Result

internal class SystemCPULoadDataSource: ISystemCPULoadDataSource {
    override fun getSystemCPULoadDataSource(): Result<Int> {
        return Result.Success(9)
    }
}