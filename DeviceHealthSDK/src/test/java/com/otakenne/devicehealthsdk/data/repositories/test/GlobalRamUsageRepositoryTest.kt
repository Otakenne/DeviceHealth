package com.otakenne.devicehealthsdk.data.repositories.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.otakenne.devicehealthsdk.data.datasources.IGlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.datasources.mock.GlobalRamUsageDataSourceMock
import com.otakenne.devicehealthsdk.data.repositories.GlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class GlobalRamUsageRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var globalRamUsageDataSourceMock: IGlobalRamUsageDataSource
    private lateinit var globalRamUsageRepository: GlobalRamUsageRepository

    @Before
    fun setup() {
        globalRamUsageDataSourceMock = GlobalRamUsageDataSourceMock(false)
        globalRamUsageRepository = GlobalRamUsageRepository(globalRamUsageDataSourceMock)
    }

    @Test
    fun getGlobalRamUsageTest() {
        val globalRamUsage = Constants.GLOBAL_RAM_USAGE
        when (val globalRamUsageFromRepo = globalRamUsageRepository.getGlobalRamUsage()) {
            is Result.Success -> Truth.assertThat(globalRamUsageFromRepo.content).isEqualTo(globalRamUsage)
            is Result.Error -> Truth.assertThat(globalRamUsageFromRepo.throwable.localizedMessage)
                .isEqualTo(Constants.GLOBAL_RAM_USAGE_ERROR_MESSAGE)
        }
    }
}