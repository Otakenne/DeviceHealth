package com.otakenne.devicehealthsdk.data.repositories.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.HistoricalAlertsRepository
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import com.otakenne.devicehealthsdk.data.repositories.mock.CacheRepositoryMock
import com.otakenne.devicehealthsdk.utility.HistoricalAlertFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HistoryAlertRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var historicalAlertFactory: HistoricalAlertFactory
    private lateinit var cacheRepositoryMock: ICacheRepository
    private lateinit var historicalAlertsRepository: HistoricalAlertsRepository

    @Before
    fun setup() {
        historicalAlertFactory = HistoricalAlertFactory()
        cacheRepositoryMock = CacheRepositoryMock()
        historicalAlertsRepository = HistoricalAlertsRepository(cacheRepositoryMock)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertHistoricalAlertTest() = runTest {
        historicalAlertsRepository.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        historicalAlertsRepository.getHistoricalAlerts().collect {
            assertThat(it.size).isEqualTo(1)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getHistoricalAlertsTest() = runTest {
        val numberOfEntries = 2
        repeat(numberOfEntries) {
            historicalAlertsRepository.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        }
        historicalAlertsRepository.getHistoricalAlerts().collect{
            assertThat(it.size).isEqualTo(numberOfEntries)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteAllHistoricalAlertsTest() = runTest {
        val numberOfEntries = 2
        repeat(numberOfEntries) {
            historicalAlertsRepository.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        }
        historicalAlertsRepository.deleteAllHistoricalAlerts()
        historicalAlertsRepository.getHistoricalAlerts().collect{
            assertThat(it.size).isEqualTo(0)
        }
    }
}