package com.otakenne.devicehealthsdk.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserSettingsViewModel(
    private val cacheRepository: ICacheRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        getUserSettings()
    }

    private fun getUserSettings() {
        getBatteryHealthThreshold()
        getGlobalRamUsageThreshold()
        getSystemCPULoadThreshold()
        getShouldShowReverseNotification()
    }

    private fun getBatteryHealthThreshold() {
        val batteryHealthThreshold = cacheRepository.getBatteryHealthThreshold()
        _uiState.value = _uiState.value.copy(batteryHealthThreshold = batteryHealthThreshold)
    }

    private fun getGlobalRamUsageThreshold() {
        val globalRamUsageThreshold = cacheRepository.getGlobalRamUsageThreshold()
        _uiState.value = _uiState.value.copy(globalRamUsageThreshold = globalRamUsageThreshold)
    }

    private fun getSystemCPULoadThreshold() {
        val systemCPULoadThreshold = cacheRepository.getSystemCPULoadThreshold()
        _uiState.value = _uiState.value.copy(systemCPULoadThreshold = systemCPULoadThreshold)
    }

    private fun getShouldShowReverseNotification() {
        val shouldShowReverseNotification = cacheRepository.getShouldShowReverseNotification()
        _uiState.value = _uiState.value.copy(shouldShowReverseNotification = shouldShowReverseNotification)
    }

    fun setBatteryHealthThreshold(batteryHealthThreshold: Float) {
        cacheRepository.setBatteryHealthThreshold(batteryHealthThreshold.toInt())
        getBatteryHealthThreshold()
    }

    fun setGlobalRamUsageThreshold(globalRamUsageThreshold: Float) {
        cacheRepository.setGlobalRamUsageThreshold(globalRamUsageThreshold.toInt())
        getGlobalRamUsageThreshold()
    }

    fun setSystemCPULoadThreshold(systemCPULoadThreshold: Float) {
        cacheRepository.setSystemCPULoadThreshold(systemCPULoadThreshold.toInt())
        getSystemCPULoadThreshold()
    }

    fun setShouldShowReverseNotification(shouldShow: Boolean) {
        cacheRepository.setShouldShowReverseNotification(shouldShow)
        getShouldShowReverseNotification()
    }

    data class UIState(
        val batteryHealthThreshold: Int = Int.DEFAULT,
        val globalRamUsageThreshold: Int = Int.DEFAULT,
        val systemCPULoadThreshold: Int = Int.DEFAULT,
        val shouldShowReverseNotification: Boolean = false
    )
}
