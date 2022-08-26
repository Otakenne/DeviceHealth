package com.otakenne.devicehealthsdk.data.datasources

import android.content.Context
import com.otakenne.devicehealthsdk.R

internal class TrackMetricToThresholdPositionDataSource(
    private val context: Context
): ITrackMetricToThresholdPositionDataSource {

    private val SHARED_PREFERENCES_NAME = "track_metric_threshold_relative_position"
    private val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun setBatteryIsAboveThreshold(isAbove: Boolean) {
        with (preferences.edit()) {
            putBoolean(context.getString(R.string.battery_is_above_threshold), isAbove)
            apply()
        }
    }

    override fun setGlobalRamIsAboveThreshold(isAbove: Boolean) {
        with (preferences.edit()) {
            putBoolean(context.getString(R.string.global_ram_is_above_threshold), isAbove)
            apply()
        }
    }

    override fun setSystemCPULoadIsAboveThreshold(isAbove: Boolean) {
        with (preferences.edit()) {
            putBoolean(context.getString(R.string.system_cpu_load_is_above_threshold), isAbove)
            apply()
        }
    }

    override fun getBatteryIsAboveThreshold(): Boolean {
        return preferences.getBoolean(
            context.getString(R.string.battery_is_above_threshold),
            false
        )
    }

    override fun getGlobalRamIsAboveThreshold(): Boolean {
        return preferences.getBoolean(
            context.getString(R.string.global_ram_is_above_threshold),
            false
        )
    }

    override fun getSystemCPULoadIsAboveThreshold(): Boolean {
        return preferences.getBoolean(
            context.getString(R.string.system_cpu_load_is_above_threshold),
            false
        )
    }

    override fun resetBatteryIsAboveThreshold() {
        with (preferences.edit()) {
            remove(context.getString(R.string.battery_is_above_threshold))
            apply()
        }
    }

    override fun resetGlobalRamIsAboveThreshold() {
        with (preferences.edit()) {
            remove(context.getString(R.string.global_ram_is_above_threshold))
            apply()
        }
    }

    override fun resetSystemCPULoadIsAboveThreshold() {
        with (preferences.edit()) {
            remove(context.getString(R.string.system_cpu_load_is_above_threshold))
            apply()
        }
    }
}