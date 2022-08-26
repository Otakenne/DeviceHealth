package com.otakenne.devicehealthsdk.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.otakenne.devicehealthsdk.data.datasources.*
import com.otakenne.devicehealthsdk.data.datasources.BatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.datasources.GlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.datasources.IBatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.datasources.IGlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.datasources.SystemCPULoadDataSource
import com.otakenne.devicehealthsdk.data.datasources.room.HistoricalAlertsDatabase
import com.otakenne.devicehealthsdk.data.datasources.room.HistoricalAlertsDatabase.Companion.DATABASE_NAME
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.*
import com.otakenne.devicehealthsdk.data.repositories.BatteryHealthRepository
import com.otakenne.devicehealthsdk.data.repositories.GlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.repositories.IBatteryHealthRepository
import com.otakenne.devicehealthsdk.data.repositories.IGlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.repositories.ISystemCPULoadRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApplicationModule {
    @Singleton
    @Provides
    fun providesApplicationContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providesBatteryHealthDataSource(
        context: Context
    ): IBatteryHealthDataSource {
        return BatteryHealthDataSource(context)
    }

    @Singleton
    @Provides
    fun providesGlobalRamUsageDataSource(
        context: Context
    ): IGlobalRamUsageDataSource {
        return GlobalRamUsageDataSource(context)
    }

    @Singleton
    @Provides
    fun providesSystemCPULoadDataSource(
        context: Context
    ): ISystemCPULoadDataSource {
        return SystemCPULoadDataSource()
    }

    @Singleton
    @Provides
    fun providesUserSettingsDataSource(
        context: Context
    ): IUserSettingsDataSource {
        return UserSettingsDataSource(context)
    }

    @Singleton
    @Provides
    fun providesTrackMetricToThresholdPositionDataSource(
        context: Context
    ): ITrackMetricToThresholdPositionDataSource {
        return TrackMetricToThresholdPositionDataSource(context)
    }

    @Singleton
    @Provides
    fun providesBatteryHealthRepository(
        dataSource: IBatteryHealthDataSource
    ): IBatteryHealthRepository {
        return BatteryHealthRepository(dataSource)
    }

    @Singleton
    @Provides
    fun providesGlobalRamUsageRepository(
        dataSource: IGlobalRamUsageDataSource
    ): IGlobalRamUsageRepository {
        return GlobalRamUsageRepository(dataSource)
    }

    @Singleton
    @Provides
    fun providesSystemCPULoadRepository(
        dataSource: ISystemCPULoadDataSource
    ): ISystemCPULoadRepository {
        return SystemCPULoadRepository(dataSource)
    }

    @Singleton
    @Provides
    fun providesUserSettingsRepository(
        cacheRepository: ICacheRepository
    ): IUserSettingsRepository {
        return UserSettingsRepository(cacheRepository)
    }

    @Singleton
    @Provides
    fun providesTrackMetricToThresholdPositionRepository(
        trackMetricToThresholdPositionDataSource: ITrackMetricToThresholdPositionDataSource
    ): ITrackMetricToThresholdPositionRepository {
        return TrackMetricToThresholdPositionRepository(trackMetricToThresholdPositionDataSource)
    }

    @Provides
    @Singleton
    fun providesHistoricalAlertsDatabase(
        application: Application
    ): HistoricalAlertsDatabase {
        return Room.databaseBuilder(
            application,
            HistoricalAlertsDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesHistoricalAlertsDao(
        historicalAlertsDatabase: HistoricalAlertsDatabase
    ): HistoricalAlert.Dao {
        return historicalAlertsDatabase.historicalAlertsDao()
    }

    @Provides
    @Singleton
    fun providesCacheRepository(
        historicalAlertsDatabase: HistoricalAlertsDatabase,
        userSettingsDataSource: IUserSettingsDataSource
    ): ICacheRepository {
        return CacheRepository(historicalAlertsDatabase, userSettingsDataSource)
    }

    @Provides
    @Singleton
    fun providesHistoricalAlertsRepository(
        cacheRepository: ICacheRepository
    ): IHistoricalAlertsRepository {
        return HistoricalAlertsRepository(cacheRepository)
    }
}