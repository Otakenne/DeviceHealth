package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.utility.Result

internal interface ISystemCPULoadRepository {
    fun getSystemCPULoad(): Result<Int>
}