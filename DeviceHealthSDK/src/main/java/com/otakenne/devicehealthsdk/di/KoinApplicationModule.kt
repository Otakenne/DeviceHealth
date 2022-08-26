package com.otakenne.devicehealthsdk.di

import androidx.room.Room
import com.otakenne.devicehealthsdk.data.datasources.*
import com.otakenne.devicehealthsdk.data.datasources.BatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.datasources.GlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.datasources.IBatteryHealthDataSource
import com.otakenne.devicehealthsdk.data.datasources.IGlobalRamUsageDataSource
import com.otakenne.devicehealthsdk.data.datasources.ISystemCPULoadDataSource
import com.otakenne.devicehealthsdk.data.datasources.room.HistoricalAlertsDatabase
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.*
import com.otakenne.devicehealthsdk.data.repositories.BatteryHealthRepository
import com.otakenne.devicehealthsdk.data.repositories.GlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.repositories.IBatteryHealthRepository
import com.otakenne.devicehealthsdk.data.repositories.IGlobalRamUsageRepository
import com.otakenne.devicehealthsdk.data.repositories.ISystemCPULoadRepository
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import com.otakenne.devicehealthsdk.ui.viewmodels.HistoricalAlertsViewModel
import com.otakenne.devicehealthsdk.ui.viewmodels.UserSettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
//    single {
//        Room.databaseBuilder(
//            get(),
//            HistoricalAlertsDatabase::class.java,
//            HistoricalAlertsDatabase.DATABASE_NAME
//        ).fallbackToDestructiveMigration().build()
//    }
}

val dataSourceModule = module {
    single <IBatteryHealthDataSource> { BatteryHealthDataSource(get()) }
    single <IGlobalRamUsageDataSource> { GlobalRamUsageDataSource(get()) }
    single <ISystemCPULoadDataSource> { SystemCPULoadDataSource() }
    single <ITrackMetricToThresholdPositionDataSource> { TrackMetricToThresholdPositionDataSource(get()) }
    single <IUserSettingsDataSource> { UserSettingsDataSource(get()) }
}

val repositoryModule = module {
    single <IBatteryHealthRepository> { BatteryHealthRepository(get()) }
    single { HistoricalAlertsDatabase.create(androidContext()) }
    single <ICacheRepository> { CacheRepository(get(), get()) }
    single <IGlobalRamUsageRepository> { GlobalRamUsageRepository(get()) }
    single <IHistoricalAlertsRepository> { HistoricalAlertsRepository(get()) }
    single <ISystemCPULoadRepository> { SystemCPULoadRepository(get()) }
    single <ITrackMetricToThresholdPositionRepository> { TrackMetricToThresholdPositionRepository(get()) }
    single <IUserSettingsRepository> { UserSettingsRepository(get()) }
}

val viewModelModule = module {
    viewModel { DeviceHealthMetricsViewModel(get(), get(), get(), get(), get()) }
    viewModel { HistoricalAlertsViewModel(get()) }
    viewModel { UserSettingsViewModel(get()) }
}

val appModules = listOf(
//    databaseModule,
    dataSourceModule,
    repositoryModule,
    viewModelModule
)