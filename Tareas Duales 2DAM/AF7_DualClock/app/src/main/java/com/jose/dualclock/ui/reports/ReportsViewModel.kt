package com.jose.dualclock.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.dualclock.data.local.room.AttendanceEntity
import com.jose.dualclock.data.local.room.ReportEntity
import com.jose.dualclock.domain.model.UserStats
import com.jose.dualclock.domain.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    val listaTrabajadores = listOf(
        "Antonio García", "María López", "Jose Rodríguez", "Ana Martínez",
        "Luis Sánchez", "Elena Pérez", "Carlos Gómez", "Laura Fernández",
        "Diego Ruiz", "Lucía Díaz", "Ivan Morales", "Marta Cano",
        "Raúl Heredia", "Sofía Vega", "Óscar Ortiz", "Rubén Ramos",
        "Sonia Blatt", "Víctor Sanz", "Paula Luna", "Javier Soto"
    )

    private val _selectedUser = MutableStateFlow(listaTrabajadores[0])
    val selectedUser: StateFlow<String> = _selectedUser.asStateFlow()

    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    private val _notificationMessage = MutableStateFlow<String?>(null)
    val notificationMessage: StateFlow<String?> = _notificationMessage.asStateFlow()

    private val _isNotificationSent = MutableStateFlow(false)
    val isNotificationSent: StateFlow<Boolean> = _isNotificationSent.asStateFlow()

    // FLUJO DE ASISTENCIA (Tab 1)
    val userAttendance: StateFlow<List<AttendanceEntity>> = repository.getAllAttendance()
        .combine(_selectedUser) { list, user -> list.filter { it.userId == user } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats: StateFlow<UserStats> = userAttendance.map { list -> calculateStats(list) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserStats())

    // FLUJO DE REPORTES DE INCIDENCIAS (Tab 2)
    val allReports: StateFlow<List<ReportEntity>> = repository.getAllReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            userAttendance.collect { _isNotificationSent.value = false }
        }
    }

    fun onUserSelected(user: String) { _selectedUser.value = user }

    fun sendWarningNotification() {
        viewModelScope.launch {
            _isSending.value = true
            delay(1500)
            _isSending.value = false
            _isNotificationSent.value = true
            _notificationMessage.value = "⚠️ Aviso enviado a ${_selectedUser.value}"
            delay(3000)
            _notificationMessage.value = null
        }
    }

    // ACCIÓN DEL ADMIN: RESOLVER INCIDENCIA
    fun resolveReport(report: ReportEntity) {
        viewModelScope.launch {
            repository.resolveReport(report.id)
            _notificationMessage.value = "✅ Incidencia resuelta"
            delay(2000)
            _notificationMessage.value = null
        }
    }

    private fun calculateStats(list: List<AttendanceEntity>): UserStats {
        if (list.isEmpty()) return UserStats()
        var totalMillis = 0L; var lateEntries = 0; var earlyExits = 0; var totalEntries = 0
        val sortedList = list.sortedBy { it.timestamp }
        var lastIn: AttendanceEntity? = null
        sortedList.forEach { record ->
            val cal = Calendar.getInstance().apply { timeInMillis = record.timestamp }
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
            if (record.type == "IN") {
                totalEntries++; lastIn = record
                if (hour > 8 || (hour == 8 && minute > 5)) lateEntries++
            } else if (record.type == "OUT" && lastIn != null) {
                totalMillis += (record.timestamp - lastIn!!.timestamp)
                if (hour < 16) earlyExits++
                lastIn = null
            }
        }
        val totalHours = totalMillis / (1000.0 * 60 * 60)
        val punctuality = if (totalEntries > 0) ((totalEntries - lateEntries) * 100 / totalEntries) else 100
        return UserStats(Math.round(totalHours * 100.0) / 100.0, punctuality, lateEntries, earlyExits)
    }
}