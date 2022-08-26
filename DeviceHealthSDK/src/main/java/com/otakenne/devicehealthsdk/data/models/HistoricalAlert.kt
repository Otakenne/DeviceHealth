package com.otakenne.devicehealthsdk.data.models

import androidx.room.*
import com.otakenne.devicehealthsdk.data.utility.DEFAULT_DATE
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import kotlinx.coroutines.flow.Flow
import java.util.*

@Entity(tableName = "historical_alerts")
data class HistoricalAlert(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val time: Date = DEFAULT_DATE,
    val historicalAlertType: String = String.DEFAULT,
    val threshold: Int = Int.DEFAULT,
    val value: Int = Int.DEFAULT
) {
    enum class HistoricalAlertType(val alertType: String) {
        BATTERY("battery_health"),
        RAM("global_ram_usage"),
        CPU("system_cpu_load")
    }

    @androidx.room.Dao
    interface Dao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertHistoricalAlert(historicalAlert: HistoricalAlert)

        @Query("SELECT * FROM historical_alerts")
        fun getHistoricalAlerts(): Flow<List<HistoricalAlert>>

        @Query("DELETE FROM historical_alerts")
        suspend fun deleteAllHistoricalAlerts()
    }
}
