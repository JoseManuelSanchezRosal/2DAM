package com.jose.dualclock.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
open class SettingsDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val EMPLOYEE_NAME_KEY = stringPreferencesKey("employee_name")
    private val ALLOWED_SSID_KEY = stringPreferencesKey("allowed_ssid")
    private val EXIT_TIME_KEY = longPreferencesKey("exit_time")

    val employeeName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[EMPLOYEE_NAME_KEY] ?: "Empleado"
        }

    val allowedSsid: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[ALLOWED_SSID_KEY] ?: "Office_WiFi"
        }

    val exitTime: Flow<Long> = context.dataStore.data
        .map { preferences ->
            preferences[EXIT_TIME_KEY] ?: (17 * 60 + 0).toLong() // Default 17:00 (in minutes from midnight)
        }

    suspend fun saveEmployeeName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[EMPLOYEE_NAME_KEY] = name
        }
    }

    suspend fun saveAllowedSsid(ssid: String) {
        context.dataStore.edit { preferences ->
            preferences[ALLOWED_SSID_KEY] = ssid
        }
    }

    suspend fun saveExitTime(timeInMinutes: Long) {
        context.dataStore.edit { preferences ->
            preferences[EXIT_TIME_KEY] = timeInMinutes
        }
    }
}
