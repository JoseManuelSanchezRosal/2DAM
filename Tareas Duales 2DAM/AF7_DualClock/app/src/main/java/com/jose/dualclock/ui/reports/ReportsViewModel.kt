package com.jose.dualclock.ui.reports

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
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

    val userAttendance: StateFlow<List<AttendanceEntity>> = repository.getAllAttendance()
        .combine(_selectedUser) { list, user -> list.filter { it.userId == user } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats: StateFlow<UserStats> = userAttendance.map { list -> calculateStats(list) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserStats())

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

    fun resolveReport(report: ReportEntity) {
        viewModelScope.launch {
            repository.resolveReport(report.id)
            _notificationMessage.value = "✅ Incidencia resuelta"
            delay(2000)
            _notificationMessage.value = null
        }
    }

    fun generatePdfReport(
        context: Context,
        userName: String,
        stats: UserStats,
        attendanceList: List<AttendanceEntity>
    ) {
        viewModelScope.launch {
            _notificationMessage.value = "Generando informe PDF..."

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 page size
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas

            val titlePaint = Paint().apply {
                color = Color.BLACK
                textSize = 24f
                isFakeBoldText = true
            }
            val headerPaint = Paint().apply {
                color = Color.BLACK
                textSize = 16f
                isFakeBoldText = true
            }
            val bodyPaint = Paint().apply {
                color = Color.DKGRAY
                textSize = 12f
            }
            val tableHeaderPaint = Paint().apply {
                color = Color.WHITE
                textSize = 12f
                isFakeBoldText = true
            }
            val tableCellPaint = Paint().apply {
                color = Color.BLACK
                textSize = 10f
            }
            val latePaint = Paint().apply{
                color = Color.RED
                textSize = 10f
                isFakeBoldText = true
            }

            var yPos = 60f

            canvas.drawText("Informe de Actividad - DualClock", 40f, yPos, titlePaint)
            yPos += 40f

            canvas.drawText("Trabajador: $userName", 40f, yPos, headerPaint)
            yPos += 25f
            canvas.drawText("Fecha de Emisión: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())}", 40f, yPos, bodyPaint)
            yPos += 40f

            canvas.drawText("Estadísticas del Periodo", 40f, yPos, headerPaint)
            yPos += 25f
            canvas.drawText("• Horas Totales Registradas: ${stats.totalHours}h", 50f, yPos, bodyPaint)
            yPos += 20f
            canvas.drawText("• Tasa de Puntualidad: ${stats.punctualityRate}%", 50f, yPos, bodyPaint)
            yPos += 20f
            canvas.drawText("• Fichajes con Retraso: ${stats.lateEntries}", 50f, yPos, bodyPaint)
            yPos += 20f
            canvas.drawText("• Salidas Anticipadas: ${stats.earlyExits}", 50f, yPos, bodyPaint)
            yPos += 40f

            canvas.drawText("Historial de Fichajes", 40f, yPos, headerPaint)
            yPos += 25f

            val headerBgPaint = Paint().apply { color = Color.parseColor("#424242") }
            canvas.drawRect(40f, yPos - 15, pageInfo.pageWidth - 40f, yPos + 10, headerBgPaint)
            canvas.drawText("Tipo", 60f, yPos, tableHeaderPaint)
            canvas.drawText("Fecha y Hora", 200f, yPos, tableHeaderPaint)
            canvas.drawText("Observación", 400f, yPos, tableHeaderPaint)
            yPos += 25f

            val sortedList = attendanceList.sortedByDescending { it.timestamp }
            for (record in sortedList) {
                if (yPos > pageInfo.pageHeight - 40) {
                    pdfDocument.finishPage(page)
                    // Aquí se crearía una nueva página si el contenido es muy largo
                    // Por simplicidad, este ejemplo no lo implementa completamente
                    break
                }

                val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(record.timestamp))
                val typeStr = if (record.type == "IN") "ENTRADA" else "SALIDA"

                var observation = ""
                val cal = Calendar.getInstance().apply { timeInMillis = record.timestamp }
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val minute = cal.get(Calendar.MINUTE)
                if (record.type == "IN" && (hour > 8 || (hour == 8 && minute > 5))) observation = "RETRASO"
                if (record.type == "OUT" && hour < 16) observation = "SALIDA ANTICIPADA"

                canvas.drawText(typeStr, 60f, yPos, tableCellPaint)
                canvas.drawText(dateStr, 200f, yPos, tableCellPaint)
                if(observation.isNotEmpty()){
                    canvas.drawText(observation, 400f, yPos, latePaint)
                }

                yPos += 20f
            }

            pdfDocument.finishPage(page)

            try {
                val file = File(context.externalCacheDir, "informe_${userName.replace(" ", "_")}.pdf")
                val fos = FileOutputStream(file)
                pdfDocument.writeTo(fos)
                pdfDocument.close()
                fos.close()

                _notificationMessage.value = "Informe guardado. Abriendo..."

                val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                val viewIntent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                val chooser = Intent.createChooser(viewIntent, "Abrir PDF con...")
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(chooser)

            } catch (e: IOException) {
                _notificationMessage.value = "Error al generar el PDF: ${e.message}"
            } finally {
                delay(3000)
                _notificationMessage.value = null
            }
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