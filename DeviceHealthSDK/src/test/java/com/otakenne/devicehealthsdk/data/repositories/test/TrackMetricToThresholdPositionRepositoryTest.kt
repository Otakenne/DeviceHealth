package com.otakenne.devicehealthsdk.data.repositories.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.datasources.ITrackMetricToThresholdPositionDataSource
import com.otakenne.devicehealthsdk.data.datasources.mock.TrackMetricToThresholdPositionDataSourceMock
import com.otakenne.devicehealthsdk.data.repositories.TrackMetricToThresholdPositionRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class TrackMetricToThresholdPositionRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var trackMetricToThresholdPositionDataSource: ITrackMetricToThresholdPositionDataSource
    private lateinit var trackMetricToThresholdPositionRepository: TrackMetricToThresholdPositionRepository

    @Before
    fun setup() {
        trackMetricToThresholdPositionDataSource = TrackMetricToThresholdPositionDataSourceMock()
        trackMetricToThresholdPositionRepository = TrackMetricToThresholdPositionRepository(trackMetricToThresholdPositionDataSource)
    }

    @Test
    fun setBatteryIsAboveThresholdTest() {
        val isAbove = true
        trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(isAbove)
        val isAboveFromDataSource = trackMetricToThresholdPositionRepository.getBatteryIsAboveThreshold()
        assertThat(isAboveFromDataSource).isEqualTo(isAbove)
    }

    @Test
    fun setGlobalRamIsAboveThresholdTest() {
        val isAbove = true
        trackMetricToThresholdPositionRepository.setGlobalRamIsAboveThreshold(isAbove)
        val isAboveFromDataSource = trackMetricToThresholdPositionRepository.getGlobalRamIsAboveThreshold()
        assertThat(isAboveFromDataSource).isEqualTo(isAbove)
    }

    @Test
    fun setSystemCPULoadIsAboveThresholdTest() {
        val isAbove = true
        trackMetricToThresholdPositionRepository.setSystemCPULoadIsAboveThreshold(isAbove)
        val isAboveFromDataSource = trackMetricToThresholdPositionRepository.getSystemCPULoadIsAboveThreshold()
        assertThat(isAboveFromDataSource).isEqualTo(isAbove)
    }

    @Test
    fun resetBatteryIsAboveThresholdTest() {
        trackMetricToThresholdPositionRepository.resetBatteryIsAboveThreshold()
        val isAboveFromDataSource = trackMetricToThresholdPositionRepository.getSystemCPULoadIsAboveThreshold()
        assertThat(isAboveFromDataSource).isEqualTo(false)
    }

    @Test
    fun resetGlobalRamIsAboveThresholdTest() {
        trackMetricToThresholdPositionRepository.resetGlobalRamIsAboveThreshold()
        val isAboveFromDataSource = trackMetricToThresholdPositionRepository.getGlobalRamIsAboveThreshold()
        assertThat(isAboveFromDataSource).isEqualTo(false)
    }

    @Test
    fun resetSystemCPULoadIsAboveThresholdTest() {
        trackMetricToThresholdPositionRepository.resetSystemCPULoadIsAboveThreshold()
        val isAboveFromDataSource = trackMetricToThresholdPositionRepository.getSystemCPULoadIsAboveThreshold()
        assertThat(isAboveFromDataSource).isEqualTo(false)
    }
}