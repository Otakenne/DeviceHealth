package com.otakenne.devicehealthsdk.data.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.otakenne.devicehealthsdk.data.datasources.room.HistoricalAlertsDatabase
import com.otakenne.devicehealthsdk.utility.HistoricalAlertFactory
import com.otakenne.devicehealthsdk.utility.getOrAwaitValueTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoricalAlertDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var historicalAlertsDatabase: HistoricalAlertsDatabase
    private lateinit var dao: HistoricalAlert.Dao
    private lateinit var historicalAlerts: MutableList<HistoricalAlert>
    private lateinit var historicalAlertFactory: HistoricalAlertFactory
    private var numberOfEntries = 5

    @Before
    fun setup() {
        historicalAlertsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HistoricalAlertsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = historicalAlertsDatabase.historicalAlertsDao()

        historicalAlerts = mutableListOf()
        historicalAlertFactory = HistoricalAlertFactory()
    }

    @Test
    fun insertHistoricalAlertTest() = runBlocking {
        repeat (numberOfEntries) {
            dao.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        }
        val historicalAlertsFromDB = dao.getHistoricalAlerts().asLiveData().getOrAwaitValueTest()
        assertThat(historicalAlertsFromDB.size).isEqualTo(numberOfEntries)
    }

    @Test
    fun deleteAllHistoricalAlerts() = runBlocking {
        repeat (numberOfEntries) {
            dao.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        }
        dao.deleteAllHistoricalAlerts()
        val historicalAlertsFromDB = dao.getHistoricalAlerts().asLiveData().getOrAwaitValueTest()
        assertThat(historicalAlertsFromDB.size).isEqualTo(0)
    }

    @Test
    fun getHistoricalAlertsWhenEmptyTest() {
        val historicalAlertsFromDB = dao.getHistoricalAlerts().asLiveData().getOrAwaitValueTest()
        assertThat(historicalAlertsFromDB.size).isEqualTo(0)
    }

    @Test
    fun getHistoricalAlertsWhenNotEmptyTest() = runBlocking {
        repeat (numberOfEntries) {
            dao.insertHistoricalAlert(historicalAlertFactory.createHistoricalAlert())
        }
        val historicalAlertsFromDB = dao.getHistoricalAlerts().asLiveData().getOrAwaitValueTest()
        assertThat(historicalAlertsFromDB.size).isEqualTo(numberOfEntries)
    }

    @After
    fun tearDown() {
        historicalAlertsDatabase.close()
    }
}