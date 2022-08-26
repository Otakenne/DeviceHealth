package com.otakenne.devicehealthsdk.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import com.otakenne.devicehealthsdk.data.repositories.mock.CacheRepositoryMock
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class UserSettingsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cacheRepository: ICacheRepository
    private lateinit var viewModel: UserSettingsViewModel

    @Before
    fun setup() {
        cacheRepository = CacheRepositoryMock()
        viewModel = UserSettingsViewModel(cacheRepository)
    }

    @Test
    fun setBatteryHealthThresholdTest() {
        val batteryHealthThreshold = 76
        viewModel.setBatteryHealthThreshold(batteryHealthThreshold.toFloat())
        assertThat(viewModel.uiState.value.batteryHealthThreshold).isEqualTo(batteryHealthThreshold)
    }

    @Test
    fun setGlobalRamUsageThresholdTest() {
        val globalRamUsageThreshold = 45
        viewModel.setGlobalRamUsageThreshold(globalRamUsageThreshold.toFloat())
        assertThat(viewModel.uiState.value.globalRamUsageThreshold).isEqualTo(globalRamUsageThreshold)
    }

    @Test
    fun setSystemCPULoadThresholdTest() {
        val systemCPULoadThreshold = 30
        viewModel.setSystemCPULoadThreshold(systemCPULoadThreshold.toFloat())
        assertThat(viewModel.uiState.value.systemCPULoadThreshold).isEqualTo(systemCPULoadThreshold)
    }

    @Test
    fun setShouldShowReverseNotificationTest() {
        val shouldShow = true
        viewModel.setShouldShowReverseNotification(shouldShow)
        assertThat(viewModel.uiState.value.shouldShowReverseNotification).isEqualTo(shouldShow)
    }
}