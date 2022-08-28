package com.otakenne.devicehealthsdk.utility.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.*
import com.otakenne.devicehealthsdk.data.utility.DEFAULT
import com.otakenne.devicehealthsdk.data.utility.doNothing
import com.otakenne.devicehealthsdk.data.utility.now
import com.otakenne.devicehealthsdk.utility.notification.NotificationService
import org.koin.java.KoinJavaComponent.inject

/***
 * Worker that allows us to alert the user of metric changes while application is in the background
 */
internal class GetMetricsWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    private val cacheRepository: ICacheRepository by inject(ICacheRepository::class.java)
    private val batteryHealthRepository: IBatteryHealthRepository by inject(IBatteryHealthRepository::class.java)
    private val globalRamUsageRepository: IGlobalRamUsageRepository by inject(IGlobalRamUsageRepository::class.java)
    private val systemCPULoadRepository: ISystemCPULoadRepository by inject(ISystemCPULoadRepository::class.java)
    private val trackMetricToThresholdPositionRepository: ITrackMetricToThresholdPositionRepository by inject(ITrackMetricToThresholdPositionRepository::class.java)

    private var batteryHealthThreshold = Int.DEFAULT
    private var globalRamUsageThreshold = Int.DEFAULT
    private var systemCPULoadThreshold = Int.DEFAULT

    private var batteryIsAboveThreshold = false
    private var globalRamUsageIsAboveThreshold = false
    private var systemCPULoadIsAboveThreshold = false

    override suspend fun doWork(): Result {
        return try {
            getThresholds()
            getMetricIsAboveThreshold()
            getAllDeviceMetrics()
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    private suspend fun getAllDeviceMetrics() {
        getDeviceMetric(batteryHealthThreshold, batteryIsAboveThreshold, HistoricalAlert.HistoricalAlertType.BATTERY)
        getDeviceMetric(globalRamUsageThreshold, globalRamUsageIsAboveThreshold, HistoricalAlert.HistoricalAlertType.RAM)
        getDeviceMetric(systemCPULoadThreshold, systemCPULoadIsAboveThreshold, HistoricalAlert.HistoricalAlertType.CPU)
    }

    private suspend fun getDeviceMetric(
        threshold: Int,
        isAboveThreshold: Boolean,
        historicalAlertType: HistoricalAlert.HistoricalAlertType
    ) {
        val metric = when (historicalAlertType) {
            HistoricalAlert.HistoricalAlertType.BATTERY -> batteryHealthRepository.getBatteryHealth()
            HistoricalAlert.HistoricalAlertType.RAM -> globalRamUsageRepository.getGlobalRamUsage()
            HistoricalAlert.HistoricalAlertType.CPU -> systemCPULoadRepository.getSystemCPULoad()
        }

        when (metric) {
            is com.otakenne.devicehealthsdk.data.utility.Result.Success -> {
                if (metric.content > threshold) {
                    if (!isAboveThreshold) {
                        val alert = HistoricalAlert(
                            time = now(),
                            historicalAlertType = historicalAlertType.name,
                            threshold = threshold,
                            value = metric.content
                        )
                        cacheRepository.insertHistoricalAlert(alert)
                        sendNotification(alert)
                    }
                    when (historicalAlertType) {
                        HistoricalAlert.HistoricalAlertType.BATTERY ->
                            trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(true)
                        HistoricalAlert.HistoricalAlertType.RAM ->
                            trackMetricToThresholdPositionRepository.setGlobalRamIsAboveThreshold(true)
                        HistoricalAlert.HistoricalAlertType.CPU ->
                            trackMetricToThresholdPositionRepository.setSystemCPULoadIsAboveThreshold(true)
                    }
                } else {
                    if (isAboveThreshold) {
                        val alert = HistoricalAlert(
                            time = now(),
                            historicalAlertType = historicalAlertType.name,
                            threshold = threshold,
                            value = metric.content
                        )
                        cacheRepository.insertHistoricalAlert(alert)
                        sendNotification(alert)
                    }
                    when (historicalAlertType) {
                        HistoricalAlert.HistoricalAlertType.BATTERY ->
                            trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(false)
                        HistoricalAlert.HistoricalAlertType.RAM ->
                            trackMetricToThresholdPositionRepository.setGlobalRamIsAboveThreshold(false)
                        HistoricalAlert.HistoricalAlertType.CPU ->
                            trackMetricToThresholdPositionRepository.setSystemCPULoadIsAboveThreshold(false)
                    }
                }
            } else -> doNothing()
        }
    }

    private suspend fun getBatteryHealth() {
        when (val batteryHealth = batteryHealthRepository.getBatteryHealth()) {
            is com.otakenne.devicehealthsdk.data.utility.Result.Success -> {
                if (batteryHealth.content > batteryHealthThreshold) {
                    if (!batteryIsAboveThreshold) {
                        val alert = HistoricalAlert(
                            time = now(),
                            historicalAlertType = HistoricalAlert.HistoricalAlertType.BATTERY.name,
                            threshold = batteryHealthThreshold,
                            value = batteryHealth.content
                        )
                        cacheRepository.insertHistoricalAlert(alert)
                        sendNotification(alert)
                    }
                    trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(true)
                } else {
                    if (batteryIsAboveThreshold) {
                        val alert = HistoricalAlert(
                            time = now(),
                            historicalAlertType = HistoricalAlert.HistoricalAlertType.BATTERY.name,
                            threshold = batteryHealthThreshold,
                            value = batteryHealth.content
                        )
                        cacheRepository.insertHistoricalAlert(alert)
                        sendNotification(alert)
                    }
                    trackMetricToThresholdPositionRepository.setBatteryIsAboveThreshold(false)
                }
            } else -> doNothing()
        }
    }

    private fun getThresholds() {
        batteryHealthThreshold = cacheRepository.getBatteryHealthThreshold()
        globalRamUsageThreshold = cacheRepository.getGlobalRamUsageThreshold()
        systemCPULoadThreshold = cacheRepository.getSystemCPULoadThreshold()
    }

    private fun getMetricIsAboveThreshold() {
        batteryIsAboveThreshold = trackMetricToThresholdPositionRepository.getBatteryIsAboveThreshold()
        globalRamUsageIsAboveThreshold = trackMetricToThresholdPositionRepository.getGlobalRamIsAboveThreshold()
        systemCPULoadIsAboveThreshold = trackMetricToThresholdPositionRepository.getSystemCPULoadIsAboveThreshold()
    }

    private fun sendNotification(historicalAlert: HistoricalAlert) {
        val notificationService = NotificationService(applicationContext)
        val notificationTitle: String
        val notificationText: String

        if (historicalAlert.value > historicalAlert.threshold) {
            notificationTitle = "Threshold alert!"
            notificationText = "${historicalAlert.historicalAlertType} has surpassed it's threshold value! Kill unused " +
                    "applications to conserve resources"
            notificationService.showThresholdAlertNotification(
                notificationTitle,
                notificationText
            )
        } else {
            notificationTitle = "Back to normal"
            notificationText = "${historicalAlert.historicalAlertType} has returned below its threshold value. Your device's" +
                        "resources are now properly managed"
            if (cacheRepository.getShouldShowReverseNotification()) {
                notificationService.showNormalAlertNotification(
                    notificationTitle,
                    notificationText
                )
            }
        }
    }
}