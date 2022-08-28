package com.otakenne.devicehealthsdk.data.datasources

import android.content.Context
import com.otakenne.devicehealthsdk.R
import com.otakenne.devicehealthsdk.data.utility.Result
import javax.inject.Inject

/***
 * Stores the user threshold and shouldShowReverseNotification settings
 */
internal class UserSettingsDataSource (
    private val context: Context
): IUserSettingsDataSource {

    private val SHARED_PREFERENCES_NAME = "user_settings"
    private val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun setBatteryHealthThreshold(threshold: Int) {
        with (preferences.edit()) {
            putInt(context.getString(R.string.battery_health_threshold_settings), threshold)
            apply()
        }
    }

    override fun setSystemCPULoadThreshold(threshold: Int) {
        with (preferences.edit()) {
            putInt(context.getString(R.string.system_cpu_load_threshold_settings), threshold)
            apply()
        }
    }

    override fun setGlobalRamUsageThreshold(threshold: Int) {
        with (preferences.edit()) {
            putInt(context.getString(R.string.global_ram_usage_threshold_settings), threshold)
            apply()
        }
    }

    override fun setShouldShowReverseNotification(shouldShow: Boolean) {
        with (preferences.edit()) {
            putBoolean(context.getString(R.string.should_show_reverse_notification_settings), shouldShow)
            apply()
        }
    }

    override fun getBatteryHealthThreshold(): Int {
        return preferences.getInt(
                context.getString(R.string.battery_health_threshold_settings),
                context.resources.getInteger(R.integer.battery_health_threshold_default)
            )
    }

    override fun getSystemCPULoadThreshold(): Int {
        return preferences.getInt(
                context.getString(R.string.system_cpu_load_threshold_settings),
                context.resources.getInteger(R.integer.system_cpu_load_threshold_default)
            )
    }

    override fun getGlobalRamUsageThreshold(): Int {
        return preferences.getInt(
                context.getString(R.string.global_ram_usage_threshold_settings),
                context.resources.getInteger(R.integer.global_ram_usage_threshold_default)
            )
    }

    override fun getShouldShowReverseNotification(): Boolean {
        return preferences.getBoolean(
                context.getString(R.string.should_show_reverse_notification_settings),
                false
            )
    }

    override fun resetBatteryHealthThreshold() {
        with (preferences.edit()) {
            remove(context.getString(R.string.battery_health_threshold_settings))
            apply()
        }
    }

    override fun resetSystemCPULoadThreshold() {
        with (preferences.edit()) {
            remove(context.getString(R.string.system_cpu_load_threshold_settings))
            apply()
        }
    }

    override fun resetGlobalRamUsageThreshold() {
        with (preferences.edit()) {
            remove(context.getString(R.string.global_ram_usage_threshold_settings))
            apply()
        }
    }

    override fun resetShouldShowReverseNotification() {
        with (preferences.edit()) {
            remove(context.getString(R.string.should_show_reverse_notification_settings))
            apply()
        }
    }
}