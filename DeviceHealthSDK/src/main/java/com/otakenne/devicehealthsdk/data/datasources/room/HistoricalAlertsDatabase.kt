package com.otakenne.devicehealthsdk.data.datasources.room

import android.content.Context
import androidx.room.*
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import org.w3c.dom.Comment
import java.util.*

@Database(
    entities = [HistoricalAlert::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(HistoricalAlertsDatabase.Converters::class)
abstract class HistoricalAlertsDatabase: RoomDatabase() {
    abstract fun historicalAlertsDao(): HistoricalAlert.Dao

    companion object {
        const val DATABASE_NAME = "historical_alerts_db"

        fun create(context: Context) =
            Room.databaseBuilder(context, HistoricalAlertsDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    class Converters {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}