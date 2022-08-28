package com.otakenne.devicehealthsdk.utility

import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.utility.now
import java.util.concurrent.atomic.AtomicInteger

class HistoricalAlertFactory {
    private val startPoint = 0
    private val counter = AtomicInteger(startPoint)
    fun createHistoricalAlert(): HistoricalAlert {
        val id = counter.incrementAndGet()

        return HistoricalAlert(
            id = id,
            time = now(),
            historicalAlertType = HistoricalAlert.HistoricalAlertType.RAM.name,
            threshold = id * 10,
            value = id * 5
        )
    }
}