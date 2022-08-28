package com.otakenne.devicehealthsdk.data.repositories.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.datasources.IBatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.datasources.mock.BatteryHealthDataSourceMock
import com.otakenne.devicehealthsdk.data.repositories.BatteryHealthRepository
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class BatteryHealthRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var batteryHealthDataSourceMock: IBatteryHealthDataSource
    private lateinit var batteryHealthRepository: BatteryHealthRepository

    @Before
    fun setup() {
        batteryHealthDataSourceMock = BatteryHealthDataSourceMock(false)
        batteryHealthRepository = BatteryHealthRepository(batteryHealthDataSourceMock)
    }

    @Test
    fun getBatteryHealthTest() {
        val batteryHealth = Constants.BATTERY_HEALTH
        when (val batteryHealthFromRepo = batteryHealthRepository.getBatteryHealth()) {
            is Result.Success -> assertThat(batteryHealthFromRepo.content).isEqualTo(batteryHealth)
            is Result.Error -> assertThat(batteryHealthFromRepo.throwable.localizedMessage).isEqualTo(Constants.BATTERY_HEALTH_ERROR_MESSAGE)
        }
    }
}