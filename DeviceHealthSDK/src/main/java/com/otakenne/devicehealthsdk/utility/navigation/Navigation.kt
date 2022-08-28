package com.otakenne.devicehealthsdk.utility.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.otakenne.devicehealthsdk.ui.composables.historicalalerts.HistoricalAlertsComposable
import com.otakenne.devicehealthsdk.ui.composables.home.DeviceHealthComposable
import com.otakenne.devicehealthsdk.ui.composables.usersettings.UserSettingsComposable
import com.otakenne.devicehealthsdk.ui.viewmodels.DeviceHealthMetricsViewModel
import com.otakenne.devicehealthsdk.ui.viewmodels.HistoricalAlertsViewModel
import com.otakenne.devicehealthsdk.ui.viewmodels.UserSettingsViewModel
import org.koin.androidx.compose.getViewModel

/**
 * Manages the navigation between composables
 */
@Composable
internal fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DeviceMetricsScreen.route) {
        composable(route = Screen.DeviceMetricsScreen.route) {
            val viewModel = getViewModel<DeviceHealthMetricsViewModel>()
            DeviceHealthComposable(navController = navController, viewModel = viewModel)
        }
        
        composable(route = Screen.UserSettingsScreen.route) {
            val viewModel = getViewModel<UserSettingsViewModel>()
            UserSettingsComposable(navController = navController, viewModel = viewModel)
        }
        
        composable(route = Screen.HistoricalAlertsScreen.route) {
            val viewModel = getViewModel<HistoricalAlertsViewModel>()
            HistoricalAlertsComposable(navController = navController, viewModel = viewModel)
        }
    }
}