package com.otakenne.devicehealthsdk.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.repositories.*
import com.otakenne.devicehealthsdk.data.repositories.mock.BatteryHealthRepositoryMock
import com.otakenne.devicehealthsdk.data.repositories.mock.CacheRepositoryMock
import com.otakenne.devicehealthsdk.data.repositories.mock.GlobalRamUsageRepositoryMock
import com.otakenne.devicehealthsdk.data.repositories.mock.SystemCPULoadRepositoryMock
import com.otakenne.devicehealthsdk.data.repositories.mock.TrackMetricToThresholdPositionRepositoryMock
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import com.otakenne.devicehealthsdk.utility.Constants
import com.otakenne.devicehealthsdk.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class DeviceHealthMetricViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var batteryHealthRepositoryMock: IBatteryHealthRepository
    private lateinit var globalRamUsageRepositoryMock: IGlobalRamUsageRepository
    private lateinit var systemCPULoadRepositoryMock: ISystemCPULoadRepository
    private lateinit var trackMetricToThresholdPositionRepositoryMock: ITrackMetricToThresholdPositionRepository
    private lateinit var cacheRepositoryMock: ICacheRepository
    private lateinit var viewModel: DeviceHealthMetricsViewModel

    @Before
    fun setup() {
        batteryHealthRepositoryMock = BatteryHealthRepositoryMock(true)
        globalRamUsageRepositoryMock = GlobalRamUsageRepositoryMock(true)
        systemCPULoadRepositoryMock = SystemCPULoadRepositoryMock(true)
        trackMetricToThresholdPositionRepositoryMock = TrackMetricToThresholdPositionRepositoryMock()
        cacheRepositoryMock = CacheRepositoryMock()
        viewModel = DeviceHealthMetricsViewModel(
            batteryHealthRepositoryMock,
            globalRamUsageRepositoryMock,
            systemCPULoadRepositoryMock,
            trackMetricToThresholdPositionRepositoryMock,
            cacheRepositoryMock
        )
    }

    @Test
    fun getBatteryHealthTest() {
        viewModel.getBatteryHealth()
        assertThat(viewModel.uiState.value.batteryHealth).isEqualTo(Constants.DEFAULT_VALUE)
        assertThat(viewModel.uiState.value.batteryHealthFailureMessage).isEqualTo(String.DEFAULT)
    }

    @Test
    fun getGlobalRamUsageTest() {
        viewModel.getGlobalRamUsage()
        assertThat(viewModel.uiState.value.globalRamUsage).isEqualTo(Constants.DEFAULT_VALUE)
        assertThat(viewModel.uiState.value.globalRamUsageFailureMessage).isEqualTo(String.DEFAULT)
    }

    @Test
    fun getSystemCPULoadTest() {
        viewModel.getSystemCPULoad()
        assertThat(viewModel.uiState.value.systemCPULoad).isEqualTo(Constants.DEFAULT_VALUE)
        assertThat(viewModel.uiState.value.systemCPULoadFailureMessage).isEqualTo(String.DEFAULT)
    }

    @Test
    fun checkForBatteryAlertsTest() = runBlocking {
        val batteryHealthThreshold = 30
        trackMetricToThresholdPositionRepositoryMock.setBatteryIsAboveThreshold(false)
        viewModel.getBatteryHealth()
        viewModel.checkForBatteryAlerts(batteryHealthThreshold)
        assertThat(viewModel.notificationState.value.threshold).isEqualTo(batteryHealthThreshold)
        assertThat(viewModel.notificationState.value.value).isEqualTo(viewModel.uiState.value.batteryHealth)
    }

    @Test
    fun checkForGlobalRamAlertsTest() = runBlocking {
        val globalRamUsageThreshold = 30
        trackMetricToThresholdPositionRepositoryMock.setGlobalRamIsAboveThreshold(false)
        viewModel.getGlobalRamUsage()
        viewModel.checkForGlobalRamAlerts(globalRamUsageThreshold)
        assertThat(viewModel.notificationState.value.threshold).isEqualTo(globalRamUsageThreshold)
        assertThat(viewModel.notificationState.value.value).isEqualTo(viewModel.uiState.value.globalRamUsage)
    }

    @Test
    fun checkForSystemCPULoadAlertsTest() = runBlocking {
        val systemCPULoadThreshold = 30
        trackMetricToThresholdPositionRepositoryMock.setSystemCPULoadIsAboveThreshold(false)
        viewModel.getSystemCPULoad()
        viewModel.checkForSystemCPULoadAlerts(systemCPULoadThreshold)
        assertThat(viewModel.notificationState.value.threshold).isEqualTo(systemCPULoadThreshold)
        assertThat(viewModel.notificationState.value.value).isEqualTo(viewModel.uiState.value.systemCPULoad)
    }

    @Test
    fun getShouldShowReverseNotificationsTest() {
        viewModel.getShouldShowReverseNotifications()
        assertThat(viewModel.uiState.value.shouldShowReverseNotification).isEqualTo(false)
    }

    @After
    fun tearDown() {
        viewModel.tearDown()
    }
}