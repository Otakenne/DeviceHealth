package com.otakenne.devicehealthsdk.data.datasources

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.os.BatteryManager
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.data.utility.Result

internal class BatteryHealthDataSource (
    private val context: Context
): IBatteryHealthDataSource {
    override fun getBatteryHealth(): Result<Int> {
        return try {
            val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager
            val batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            Result.Success(batteryLevel)
        } catch (exception: Exception) {
            Result.Error(Throwable(context.getString(R.string.battery_health_error_message)))
        }
    }
}