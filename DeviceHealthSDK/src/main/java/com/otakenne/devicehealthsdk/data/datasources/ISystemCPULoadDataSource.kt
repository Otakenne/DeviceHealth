package com.otakenne.devicehealthsdk.data.datasources

import com.otakenne.devicehealthsdk.data.utility.Result

internal interface ISystemCPULoadDataSource {
    fun getSystemCPULoadDataSource(): Result<Int>
}