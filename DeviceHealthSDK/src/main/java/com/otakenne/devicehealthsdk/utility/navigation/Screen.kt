package com.otakenne.devicehealthsdk.utility.navigation

internal sealed class Screen(val route: String) {
    object DeviceMetricsScreen: Screen("device_metrics_screen")
    object UserSettingsScreen: Screen("user_settings_screen")
    object HistoricalAlertsScreen: Screen("historical_alerts_screen")
}
