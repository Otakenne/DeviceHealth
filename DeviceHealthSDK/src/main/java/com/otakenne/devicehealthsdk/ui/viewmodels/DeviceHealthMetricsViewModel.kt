package com.otakenne.devicehealthsdk.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.*
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.data.utility.now
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class DeviceHealthMetricsViewModel (
    private val batteryHealthRepository: IBatteryHealthRepository,
    private val globalRamUsageRepository: IGlobalRamUsageRepository,
    private val systemCPULoadRepository: ISystemCPULoadRepository,
    private val trackMetricToThresholdPositionRepository: ITrackMetricToThresholdPositionRepository,
    private val cacheRepository: ICacheRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _notificationState = MutableStateFlow(HistoricalAlert())
    val notificationState: StateFlow<HistoricalAlert> = _notificationState

    private var pollJob: Job

    init {
        pollJob = startPollJob()
    }

    fun getBatteryHealth() {
        when (val batteryHealth = batteryHealthRepository.getBatteryHealth()) {
            is Result.Success ->  _uiState.value = _uiState.value.copy(
                batteryHealth = batteryHealth.content,
                batteryHealthFailureMessage = String.DEFAULT
            )
            is Result.Error -> _uiState.value = _uiState.value.copy(
                batteryHealth = Int.DEFAULT,
                batteryHealthFailureMessage = batteryHealth.throwable.message ?: "Couldn't get battery metric"
            )
        }
    }

    fun getGlobalRamUsage() {
        when (val globalRamUsage = globalRamUsageRepository.getGlobalRamUsage()) {
            is Result.Success -> _uiState.value = _uiState.value.copy(
                globalRamUsage = globalRamUsage.content,
                globalRamUsageFailureMessage = String.DEFAULT
            )
            is Result.Error -> _uiState.value = _uiState.value.copy(
                globalRamUsage = Int.DEFAULT,
                globalRamUsageFailureMessage = globalRamUsage.throwable.message ?: "Couldn't get ram metric"
            )
        }
    }

    fun getSystemCPULoad() {
        when (val systemCPULoad = systemCPULoadRepository.getSystemCPULoad()) {
            is Result.Success -> _uiState.value = _uiState.value.copy(
                systemCPULoad = systemCPULoad.content,
                systemCPULoadFailureMessage = String.DEFAULT
            )
            is Result.Error -> _uiState.value = _uiState.value.copy(
                systemCPULoad = Int.DEFAULT,
                systemCPULoadFailureMessage = systemCPULoad.throwable.message ?:  "Couldn't get cpu metric"
            )
        }
    }

    private fun getAllMetrics() {
        getBatteryHealth()
        getGlobalRamUsage()
        getSystemCPULoad()
    }

    suspend fun checkForBatteryAlerts(batteryHealthThreshold: Int) {
        if (_uiState.value.batteryHealth > batteryHealthThreshold) {
            if (!trackMetricToThresholdPositionRepository.getBatteryIsAboveThreshold()) {
                val alert = HistoricalAlert(
                    time = now(),
                    historicalAlertType = HistoricalAlert.HistoricalAlertType.BATTERY.name,
                    threshold = batteryHealthThreshold,
                    value = _uiState.value.batteryHealth
                )
                _notificationState.value  = alert
                cacheRepository.insertHistoricalAlert(alert)
            }
            trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(true)
        } else {
            if (trackMetricToThresholdPositionRepository.getBatteryIsAboveThreshold()) {
                val alert = HistoricalAlert(
                    time = now(),
                    historicalAlertType = HistoricalAlert.HistoricalAlertType.BATTERY.name,
                    threshold = batteryHealthThreshold,
                    value = _uiState.value.batteryHealth
                )
                _notificationState.value = alert
            }
            trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(false)
        }
    }

    suspend fun checkForGlobalRamAlerts(globalRamUsageThreshold: Int) {
        if (_uiState.value.globalRamUsage > globalRamUsageThreshold) {
            if (!trackMetricToThresholdPositionRepository.getGlobalRamIsAboveThreshold()) {
                val alert = HistoricalAlert(
                    time = now(),
                    historicalAlertType = HistoricalAlert.HistoricalAlertType.RAM.name,
                    threshold = globalRamUsageThreshold,
                    value = _uiState.value.globalRamUsage
                )
                _notificationState.value = alert
                cacheRepository.insertHistoricalAlert(alert)
            }
            trackMetricToThresholdPositionRepository.setGlobalRamIsAboveThreshold(true)
        } else {
            if (trackMetricToThresholdPositionRepository.getGlobalRamIsAboveThreshold()) {
                val alert = HistoricalAlert(
                    time = now(),
                    historicalAlertType = HistoricalAlert.HistoricalAlertType.RAM.name,
                    threshold = globalRamUsageThreshold,
                    value = _uiState.value.globalRamUsage
                )
                _notificationState.value = alert
            }
            trackMetricToThresholdPositionRepository.setGlobalRamIsAboveThreshold(false)
        }
    }

    suspend fun checkForSystemCPULoadAlerts(systemCPULoadThreshold: Int) {
        if (_uiState.value.systemCPULoad > systemCPULoadThreshold) {
            if (!trackMetricToThresholdPositionRepository.getSystemCPULoadIsAboveThreshold()) {
                val alert = HistoricalAlert(
                    time = now(),
                    historicalAlertType = HistoricalAlert.HistoricalAlertType.CPU.name,
                    threshold = systemCPULoadThreshold,
                    value = _uiState.value.systemCPULoad
                )
                _notificationState.value = alert
                cacheRepository.insertHistoricalAlert(alert)
            }
            trackMetricToThresholdPositionRepository.setSystemCPULoadIsAboveThreshold(true)
        } else {
            if (trackMetricToThresholdPositionRepository.getSystemCPULoadIsAboveThreshold()) {
                val alert = HistoricalAlert(
                    time = now(),
                    historicalAlertType = HistoricalAlert.HistoricalAlertType.CPU.name,
                    threshold = systemCPULoadThreshold,
                    value = _uiState.value.systemCPULoad
                )
                _notificationState.value = alert
            }
            trackMetricToThresholdPositionRepository.setSystemCPULoadIsAboveThreshold(false)
        }
    }

    private suspend fun checkForAllPotentialAlerts() {
        val batteryHealthThreshHold = cacheRepository.getBatteryHealthThreshold()
        val globalRamUsageThreshHold = cacheRepository.getGlobalRamUsageThreshold()
        val systemCPULoadThreshHold = cacheRepository.getSystemCPULoadThreshold()

        checkForBatteryAlerts(batteryHealthThreshHold)
        checkForGlobalRamAlerts(globalRamUsageThreshHold)
        checkForSystemCPULoadAlerts(systemCPULoadThreshHold)
    }

    fun getShouldShowReverseNotifications() {
        _uiState.value = _uiState.value.copy(
            shouldShowReverseNotification = cacheRepository.getShouldShowReverseNotification()
        )
    }

    private fun startPollJob(timeInterval: Long = 5000L): Job {
        return viewModelScope.launch {
            while (isActive) {
                getAllMetrics()
                checkForAllPotentialAlerts()
                getShouldShowReverseNotifications()
                delay(timeInterval)
            }
        }
    }

    fun tearDown() {
        pollJob.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        pollJob.cancel()
    }

    data class UIState(
        val batteryHealth: Int = Int.DEFAULT,
        val globalRamUsage: Int = Int.DEFAULT,
        val systemCPULoad: Int = Int.DEFAULT,
        val shouldShowReverseNotification: Boolean = true,
        val batteryHealthFailureMessage: String = String.DEFAULT,
        val globalRamUsageFailureMessage: String = String.DEFAULT,
        val systemCPULoadFailureMessage: String = String.DEFAULT,
    )
}

