package com.otakenne.devicehealthsdk.data.repositories.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.otakenne.devicehealthsdk.data.datasources.ISystemCPULoadDataSource
import com.otakenne.devicehealthsdk.data.datasources.mock.SystemCPULoadDataSourceMock
import com.otakenne.devicehealthsdk.data.repositories.SystemCPULoadRepository
import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.utility.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SystemCPULoadRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var systemCPULoadDataSourceMock: ISystemCPULoadDataSource
    private lateinit var systemCPULoadRepository: SystemCPULoadRepository

    @Before
    fun setup() {
        systemCPULoadDataSourceMock = SystemCPULoadDataSourceMock(true)
        systemCPULoadRepository = SystemCPULoadRepository(systemCPULoadDataSourceMock)
    }

    @Test
    fun getSystemCPULoadTest() {
        val systemCPULoad = Constants.SYSTEM_CPU_LOAD
        when (val systemCPULoadFromRepo = systemCPULoadRepository.getSystemCPULoad()) {
            is Result.Success -> Truth.assertThat(systemCPULoadFromRepo.content).isEqualTo(systemCPULoad)
            is Result.Error -> Truth.assertThat(systemCPULoadFromRepo.throwable.localizedMessage)
                .isEqualTo(Constants.SYSTEM_CPU_LOAD_ERROR_MESSAGE)
        }
    }
}