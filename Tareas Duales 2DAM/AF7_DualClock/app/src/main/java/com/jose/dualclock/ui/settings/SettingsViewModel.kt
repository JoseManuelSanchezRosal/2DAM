package com.jose.dualclock.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.dualclock.data.local.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Settings Screen.
 * Manages user preferences such as employee name, allowed SSID, and exit time.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    // Expose flows directly for UI
    val employeeName: StateFlow<String> = settingsDataStore.employeeName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val allowedSsid: StateFlow<String> = settingsDataStore.allowedSsid
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    
    // We handle the Long <-> String conversion for time here or in UI
    // For simplicity, let's expose raw minutes and handle UI formatting in Composable or helper
    val exitTimeMinutes: StateFlow<Long> = settingsDataStore.exitTime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 17 * 60L) // Default 17:00

    fun saveEmployeeName(name: String) {
        viewModelScope.launch {
            settingsDataStore.saveEmployeeName(name)
        }
    }

    fun saveAllowedSsid(ssid: String) {
        viewModelScope.launch {
            settingsDataStore.saveAllowedSsid(ssid)
        }
    }

    fun saveExitTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            val totalMinutes = (hour * 60 + minute).toLong()
            settingsDataStore.saveExitTime(totalMinutes)
        }
    }
}
