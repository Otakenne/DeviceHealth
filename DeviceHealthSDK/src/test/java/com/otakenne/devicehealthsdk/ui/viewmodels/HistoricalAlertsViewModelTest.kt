package com.otakenne.devicehealthsdk.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import com.otakenne.devicehealthsdk.data.repositories.mock.CacheRepositoryMock
import com.otakenne.devicehealthsdk.utility.HistoricalAlertFactory
import com.otakenne.devicehealthsdk.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HistoricalAlertsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var historicalAlertFactory: HistoricalAlertFactory
    private lateinit var cacheRepository: ICacheRepository
    private lateinit var viewModel: HistoricalAlertsViewModel

    @Before
    fun setup() {
        historicalAlertFactory = HistoricalAlertFactory()
        cacheRepository = CacheRepositoryMock()
        viewModel = HistoricalAlertsViewModel(cacheRepository)
    }

    @Test
    fun getHistoricalAlertsTest() = runBlocking {
        val numberOfInserts = 4
        repeat(numberOfInserts) {
            cacheRepository.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        }
        viewModel.getHistoricalAlerts()
        delay(5000)
        val list = viewModel.uiState.value
        assertThat(list).isNotEqualTo(null)
    }
}