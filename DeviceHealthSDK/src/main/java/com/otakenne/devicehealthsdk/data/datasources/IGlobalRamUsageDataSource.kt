package com.otakenne.devicehealthsdk.data.datasources

import com.otakenne.devicehealthsdk.data.utility.Result

internal interface IGlobalRamUsageDataSource {
    fun getGlobalRamUsage(): Result<Int>
}