package com.otakenne.devicehealthsdk.data.repositories.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import com.otakenne.devicehealthsdk.data.repositories.UserSettingsRepository
import com.otakenne.devicehealthsdk.data.repositories.mock.CacheRepositoryMock
import com.otakenne.devicehealthsdk.utility.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class UserSettingsRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cacheRepositoryMock: ICacheRepository
    private lateinit var userSettingsRepository: UserSettingsRepository

    @Before
    fun setup() {
        cacheRepositoryMock = CacheRepositoryMock()
        userSettingsRepository = UserSettingsRepository(cacheRepositoryMock)
    }

    @Test
    fun setBatteryHealthThresholdTest() {
        val threshold = 78
        userSettingsRepository.setBatteryHealthThreshold(threshold)
        val thresholdFromDataSource = userSettingsRepository.getBatteryHealthThreshold()
        Truth.assertThat(thresholdFromDataSource).isEqualTo(threshold)
    }

    @Test
    fun setSystemCPULoadThresholdTest() {
        val threshold = 81
        userSettingsRepository.setSystemCPULoadThreshold(threshold)
        val thresholdFromDataSource = userSettingsRepository.getSystemCPULoadThreshold()
        Truth.assertThat(thresholdFromDataSource).isEqualTo(threshold)
    }

    @Test
    fun setGlobalRamUsageThresholdTest() {
        val threshold = 56
        userSettingsRepository.setGlobalRamUsageThreshold(threshold)
        val thresholdFromDataSource = userSettingsRepository.getGlobalRamUsageThreshold()
        Truth.assertThat(thresholdFromDataSource).isEqualTo(threshold)
    }

    @Test
    fun setShouldShowReverseNotificationTest() {
        val shouldShow = true
        userSettingsRepository.setShouldShowReverseNotification(shouldShow)
        val shouldShowFromDataSource = userSettingsRepository.getShouldShowReverseNotification()
        Truth.assertThat(shouldShowFromDataSource).isEqualTo(shouldShow)
    }

    @Test
    fun resetBatteryHealthThresholdTest() {
        userSettingsRepository.resetBatteryHealthThreshold()
        val thresholdFromDataSource = userSettingsRepository.getBatteryHealthThreshold()
        Truth.assertThat(thresholdFromDataSource).isEqualTo(Constants.DEFAULT_VALUE)
    }

    @Test
    fun resetSystemCPULoadThresholdTest() {
        userSettingsRepository.resetSystemCPULoadThreshold()
        val thresholdFromDataSource = userSettingsRepository.getSystemCPULoadThreshold()
        Truth.assertThat(thresholdFromDataSource).isEqualTo(Constants.DEFAULT_VALUE)
    }

    @Test
    fun resetGlobalRamUsageThresholdTest() {
        userSettingsRepository.resetGlobalRamUsageThreshold()
        val thresholdFromDataSource = userSettingsRepository.getGlobalRamUsageThreshold()
        Truth.assertThat(thresholdFromDataSource).isEqualTo(Constants.DEFAULT_VALUE)
    }

    @Test
    fun resetShouldShowReverseNotificationTest() {
        userSettingsRepository.resetShouldShowReverseNotification()
        val shouldShowFromDataSource = userSettingsRepository.getShouldShowReverseNotification()
        Truth.assertThat(shouldShowFromDataSource).isEqualTo(false)
    }
}