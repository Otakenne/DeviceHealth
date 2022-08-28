package com.otakenne.devicehealthsdk.data.repositories.mock

import com.otakenne.devicehealthsdk.data.repositories.IUserSettingsRepository
import com.otakenne.devicehealthsdk.utility.Constants

internal class UserSettingsRepositoryMock: IUserSettingsRepository {

    private var batteryHealthThreshold: Int = Constants.DEFAULT_VALUE
    private var globalRamUsageThreshold: Int = Constants.DEFAULT_VALUE
    private var systemCPULoadThreshold: Int = Constants.DEFAULT_VALUE
    private var shouldShowReverseNotification: Boolean = false

    override fun setBatteryHealthThreshold(threshold: Int) {
        batteryHealthThreshold = threshold
    }

    override fun setSystemCPULoadThreshold(threshold: Int) {
        systemCPULoadThreshold = threshold
    }

    override fun setGlobalRamUsageThreshold(threshold: Int) {
        globalRamUsageThreshold = threshold
    }

    override fun setShouldShowReverseNotification(shouldShow: Boolean) {
        shouldShowReverseNotification = shouldShow
    }

    override fun getBatteryHealthThreshold(): Int {
        return batteryHealthThreshold
    }

    override fun getSystemCPULoadThreshold(): Int {
        return systemCPULoadThreshold
    }

    override fun getGlobalRamUsageThreshold(): Int {
        return globalRamUsageThreshold
    }

    override fun getShouldShowReverseNotification(): Boolean {
        return shouldShowReverseNotification
    }

    override fun resetBatteryHealthThreshold() {
        batteryHealthThreshold = 50
    }

    override fun resetSystemCPULoadThreshold() {
        systemCPULoadThreshold = 50
    }

    override fun resetGlobalRamUsageThreshold() {
        globalRamUsageThreshold = 50
    }

    override fun resetShouldShowReverseNotification() {
        shouldShowReverseNotification = false
    }
}