package com.otakenne.devicehealthsdk.data.datasources

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import com.otakenne.devicehealthsdk.data.utility.Result

/***
 * Get global RAM usage datasource implementation
 */
internal class GlobalRamUsageDataSource (
    private val context: Context
): IGlobalRamUsageDataSource {
    override fun getGlobalRamUsage(): Result<Int> {
        return try {
            val mi = ActivityManager.MemoryInfo()
            val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
            activityManager!!.getMemoryInfo(mi)
            val percentAvail: Double = (mi.availMem / mi.totalMem.toDouble()) * 100.0
            Result.Success((100 - percentAvail.toInt()))
        } catch (exception: Exception) {
            Result.Error(Throwable(exception.localizedMessage))
        }
    }
}