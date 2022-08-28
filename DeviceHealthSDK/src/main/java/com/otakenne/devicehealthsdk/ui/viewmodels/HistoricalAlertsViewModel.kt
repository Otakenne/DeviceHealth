package com.otakenne.devicehealthsdk.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otakenne.devicehealthsdk.data.models.HistoricalAlert
import com.otakenne.devicehealthsdk.data.repositories.ICacheRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HistoricalAlertsViewModel (
    private val cacheRepository: ICacheRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    init {
        getHistoricalAlerts()
    }

    fun getHistoricalAlerts() {
        try {
            viewModelScope.launch {
                cacheRepository.getHistoricalAlerts().collect {
                    _uiState.value = _uiState.value.copy(historicalAlerts = it, initState = InitState.LOADED)
                }
            }
        } catch (exception: Exception) {
            _uiState.value = _uiState.value.copy(historicalAlerts = listOf(), initState = InitState.ERROR)
        }
    }

    data class UIState(
        val historicalAlerts: List<HistoricalAlert> = listOf(),
        val initState: InitState = InitState.LOADING
    )

    enum class InitState {
        LOADING, LOADED, ERROR
    }
}