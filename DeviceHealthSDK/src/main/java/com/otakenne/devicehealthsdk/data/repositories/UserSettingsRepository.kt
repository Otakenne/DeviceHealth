package com.otakenne.devicehealthsdk.data.repositories

import com.otakenne.devicehealthsdk.data.utility.Result
import javax.inject.Inject

internal class UserSettingsRepository (
    private val cacheRepository: ICacheRepository
): IUserSettingsRepository {
    override fun setBatteryHealthThreshold(threshold: Int) {
        cacheRepository.setBatteryHealthThreshold(threshold)
    }

    override fun setSystemCPULoadThreshold(threshold: Int) {
        cacheRepository.setSystemCPULoadThreshold(threshold)
    }

    override fun setGlobalRamUsageThreshold(threshold: Int) {
        cacheRepository.setGlobalRamUsageThreshold(threshold)
    }

    override fun setShouldShowReverseNotification(shouldShow: Boolean) {
        cacheRepository.setShouldShowReverseNotification(shouldShow)
    }

    override fun getBatteryHealthThreshold(): Int {
        return cacheRepository.getBatteryHealthThreshold()
    }

    override fun getSystemCPULoadThreshold(): Int {
        return cacheRepository.getSystemCPULoadThreshold()
    }

    override fun getGlobalRamUsageThreshold(): Int {
        return cacheRepository.getGlobalRamUsageThreshold()
    }

    override fun getShouldShowReverseNotification(): Boolean {
        return cacheRepository.getShouldShowReverseNotification()
    }

    override fun resetBatteryHealthThreshold() {
        cacheRepository.resetBatteryHealthThreshold()
    }

    override fun resetSystemCPULoadThreshold() {
        cacheRepository.resetSystemCPULoadThreshold()
    }

    override fun resetGlobalRamUsageThreshold() {
        cacheRepository.resetGlobalRamUsageThreshold()
    }

    override fun resetShouldShowReverseNotification() {
        cacheRepository.resetShouldShowReverseNotification()
    }
}