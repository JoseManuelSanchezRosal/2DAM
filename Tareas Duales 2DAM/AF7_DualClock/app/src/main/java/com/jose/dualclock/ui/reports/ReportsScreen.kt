package com.jose.dualclock.ui.reports

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jose.dualclock.data.local.room.AttendanceEntity
import com.jose.dualclock.data.local.room.ReportEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val selectedUser by viewModel.selectedUser.collectAsState()
    val attendanceList by viewModel.userAttendance.collectAsState()
    val stats by viewModel.stats.collectAsState()
    val reportsList by viewModel.allReports.collectAsState()

    val isSending by viewModel.isSending.collectAsState()
    val notificationMessage by viewModel.notificationMessage.collectAsState()
    val isNotificationSent by viewModel.isNotificationSent.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Control Horario", "Buzón Incidencias")

    var expanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(notificationMessage) {
        notificationMessage?.let { scope.launch { snackbarHostState.showSnackbar(it) } }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Panel Administrador") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (selectedTab == 0) { // Solo mostrar si estamos en la pestaña de Control Horario
                        IconButton(onClick = {
                            viewModel.generatePdfReport(context, selectedUser, stats, attendanceList)
                        }) {
                            Icon(Icons.Filled.Share, contentDescription = "Compartir PDF")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            if (selectedTab == 0) {
                AttendanceControlView(
                    viewModel, selectedUser, expanded,
                    { expanded = it }, stats, isNotificationSent, isSending, attendanceList
                )
            } else {
                ReportsInboxView(reportsList, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceControlView(
    viewModel: ReportsViewModel,
    selectedUser: String,
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    stats: com.jose.dualclock.domain.model.UserStats,
    isNotificationSent: Boolean,
    isSending: Boolean,
    attendanceList: List<AttendanceEntity>
) {
    Column(modifier = Modifier.padding(16.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { setExpanded(!expanded) },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedUser,
                onValueChange = {},
                readOnly = true,
                label = { Text("Trabajador a auditar") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { setExpanded(false) }) {
                viewModel.listaTrabajadores.forEach { user ->
                    DropdownMenuItem(text = { Text(user) }, onClick = { viewModel.onUserSelected(user); setExpanded(false) })
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            val hoursProgress = (stats.totalHours / 8.0).toFloat().coerceIn(0f, 1f)
            val hoursColor = if (stats.totalHours >= 8.0) Color(0xFF4CAF50) else Color(0xFFFF9800)
            DonutChart(hoursProgress, "${stats.totalHours}h", "Horas Totales", hoursColor)

            val punctualityColor = when {
                stats.punctualityRate == 100 -> Color(0xFF4CAF50)
                stats.punctualityRate >= 80 -> Color(0xFFFF9800)
                else -> Color(0xFFF44336)
            }
            DonutChart(stats.punctualityRate / 100f, "${stats.punctualityRate}%", "Puntualidad", punctualityColor)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (stats.lateEntries > 0 || stats.earlyExits > 0) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("⚠ Alertas Activas:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                    if (stats.lateEntries > 0) Text("• ${stats.lateEntries} retrasos.", color = MaterialTheme.colorScheme.onErrorContainer)
                    if (stats.earlyExits > 0) Text("• ${stats.earlyExits} salidas antes.", color = MaterialTheme.colorScheme.onErrorContainer)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (!isNotificationSent) {
                        Button(
                            onClick = { viewModel.sendWarningNotification() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            enabled = !isSending
                        ) {
                            if (isSending) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onError)
                            } else {
                                Icon(Icons.Filled.Notifications, null)
                                Text(" NOTIFICAR FALTA")
                            }
                        }
                    } else {
                        Text("✔ Aviso enviado", fontStyle = FontStyle.Italic, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(attendanceList.sortedByDescending { it.timestamp }) { record -> AttendanceRow(record) }
        }
    }
}

@Composable
fun ReportsInboxView(reports: List<ReportEntity>, viewModel: ReportsViewModel) {
    if (reports.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay incidencias pendientes", color = Color.Gray)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reports) { report ->
                ReportItemRow(report) { viewModel.resolveReport(report) }
            }
        }
    }
}

@Composable
fun ReportItemRow(report: ReportEntity, onResolve: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())

    Card(elevation = CardDefaults.cardElevation(2.dp)) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = report.userId, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = report.description, style = MaterialTheme.typography.bodyMedium)
                Text(text = dateFormat.format(Date(report.timestamp)), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
            IconButton(onClick = onResolve) {
                Icon(Icons.Filled.Check, contentDescription = "Resolver", tint = Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
fun DonutChart(percentage: Float, displayValue: String, label: String, color: Color, radius: Dp = 60.dp, strokeWidth: Dp = 12.dp) {
    val animatedProgress by animateFloatAsState(targetValue = percentage, animationSpec = tween(1000), label = "progress")
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(radius * 2)) {
                drawArc(Color.LightGray.copy(0.3f), 0f, 360f, false, style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round))
                drawArc(color, -90f, 360 * animatedProgress, false, style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round))
            }
            Text(displayValue, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun AttendanceRow(record: AttendanceEntity) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val cal = Calendar.getInstance().apply { timeInMillis = record.timestamp }
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    var isLate = false; var isEarly = false
    if (record.type == "IN" && (hour > 8 || (hour == 8 && minute > 5))) isLate = true
    if (record.type == "OUT" && hour < 16) isEarly = true

    val cardColor = if (isLate || isEarly) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isLate || isEarly) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Card(colors = CardDefaults.cardColors(containerColor = cardColor), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(if (record.type == "IN") "ENTRADA" else "SALIDA", fontWeight = FontWeight.Bold, color = if (record.type == "IN") Color(0xFF2E7D32) else Color(0xFFC62828))
                Text(dateFormat.format(Date(record.timestamp)), style = MaterialTheme.typography.bodyMedium, color = textColor)
            }
            if (isLate) Text("TARDE", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
            if (isEarly) Text("ANTICIPADO", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
        }
    }
}