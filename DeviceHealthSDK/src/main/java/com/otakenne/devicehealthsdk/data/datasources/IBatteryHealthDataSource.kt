package com.otakenne.devicehealthsdk.data.datasources

import com.otakenne.devicehealthsdk.data.utility.Result

internal interface IBatteryHealthDataSource {
    fun getBatteryHealth(): Result<Int>
}