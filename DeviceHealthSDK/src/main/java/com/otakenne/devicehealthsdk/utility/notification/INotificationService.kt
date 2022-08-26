package com.otakenne.devicehealthsdk.utility.notification

internal interface INotificationService {
    fun showThresholdAlertNotification(title: String, text: String)
    fun showNormalAlertNotification(title: String, text: String)
}