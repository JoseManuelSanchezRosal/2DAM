package com.jose.dualclock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.dualclock.data.local.datastore.SettingsDataStore
import com.jose.dualclock.domain.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AttendanceRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val trabajadores = listOf(
        "Antonio García", "María López", "Jose Rodríguez", "Ana Martínez",
        "Luis Sánchez", "Elena Pérez", "Carlos Gómez", "Laura Fernández",
        "Diego Ruiz", "Lucía Díaz", "Ivan Morales", "Marta Cano",
        "Raúl Heredia", "Sofía Vega", "Óscar Ortiz", "Rubén Ramos",
        "Sonia Blatt", "Víctor Sanz", "Paula Luna", "Javier Soto"
    )

    private val _currentTime = MutableStateFlow("")
    val currentTime: StateFlow<String> = _currentTime.asStateFlow()

    private val _selectedUser = MutableStateFlow(trabajadores[0])
    val selectedUser: StateFlow<String> = _selectedUser.asStateFlow()

    private val _attendanceState = MutableStateFlow<String?>(null)
    val attendanceState: StateFlow<String?> = _attendanceState.asStateFlow()

    private val _isReporting = MutableStateFlow(false)
    val isReporting: StateFlow<Boolean> = _isReporting.asStateFlow()

    private val _reportResult = MutableStateFlow<String?>(null)
    val reportResult: StateFlow<String?> = _reportResult.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    init {
        startClock()
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                sdf.timeZone = TimeZone.getTimeZone("Europe/Madrid")
                _currentTime.value = sdf.format(Date())
                val delayMillis = 1000 - (System.currentTimeMillis() % 1000)
                delay(delayMillis)
            }
        }
    }

    fun onUserSelected(name: String) {
        _selectedUser.value = name
        viewModelScope.launch {
            settingsDataStore.saveEmployeeName(name)
        }
    }

    fun loginAsAdmin(password: String, onSuccess: () -> Unit) {
        if (password == "admin1234") {
            _isAdmin.value = true
            _loginError.value = null
            onSuccess()
        } else {
            _loginError.value = "Contraseña incorrecta"
        }
    }

    fun checkIn() {
        viewModelScope.launch {
            val result = repository.checkIn()
            handleResult(result, "Entrada")
        }
    }

    fun checkOut() {
        viewModelScope.launch {
            val result = repository.checkOut()
            handleResult(result, "Salida")
        }
    }

    fun simulatePerfectDay() {
        viewModelScope.launch {
            repository.simulateAttendance(getTodayAt(8, 0), "IN")
            val result = repository.simulateAttendance(getTodayAt(16, 5), "OUT")
            handleResult(result, "Día Perfecto")
        }
    }

    fun simulateLateEntry() {
        viewModelScope.launch {
            val result = repository.simulateAttendance(getTodayAt(9, 30), "IN")
            repository.simulateAttendance(getTodayAt(16, 0), "OUT")
            handleResult(result, "Simulado: Entrada Tarde")
        }
    }

    fun simulateEarlyExit() {
        viewModelScope.launch {
            repository.simulateAttendance(getTodayAt(8, 0), "IN")
            val result = repository.simulateAttendance(getTodayAt(14, 0), "OUT")
            handleResult(result, "Simulado: Salida Anticipada")
        }
    }

    fun simulateDisaster() {
        viewModelScope.launch {
            repository.simulateAttendance(getTodayAt(10, 0), "IN")
            val result = repository.simulateAttendance(getTodayAt(13, 0), "OUT")
            handleResult(result, "Simulado: Día Desastre")
        }
    }

    private fun getTodayAt(hour: Int, minute: Int): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        return cal.timeInMillis
    }

    private fun handleResult(result: Result<Long>, action: String) {
        if (result.isSuccess) {
            _attendanceState.value = "$action registrada"
        } else {
            _attendanceState.value = "Error: ${result.exceptionOrNull()?.message}"
        }
        viewModelScope.launch {
            delay(3000)
            _attendanceState.value = null
        }
    }

    // --- CORRECCIÓN AQUÍ ---
    fun submitReport(description: String) {
        viewModelScope.launch {
            _isReporting.value = true

            // Obtenemos el usuario ACTUALMENTE seleccionado en el desplegable
            val currentUser = _selectedUser.value

            // Pasamos explícitamente el nombre al repositorio
            val result = repository.reportIssue(currentUser, description)

            _isReporting.value = false
            _reportResult.value = if (result.isSuccess) "Reporte enviado" else "Error"
            delay(3000)
            _reportResult.value = null
        }
    }
}