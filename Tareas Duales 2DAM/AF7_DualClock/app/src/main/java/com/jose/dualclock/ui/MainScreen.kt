package com.jose.dualclock.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToReports: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val currentTime by viewModel.currentTime.collectAsState()
    val selectedUser by viewModel.selectedUser.collectAsState()
    val attendanceState by viewModel.attendanceState.collectAsState()
    val isReporting by viewModel.isReporting.collectAsState()
    val reportResult by viewModel.reportResult.collectAsState()

    val isAdmin by viewModel.isAdmin.collectAsState()
    val loginError by viewModel.loginError.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var reportDescription by remember { mutableStateOf("") }

    var showAdminLogin by remember { mutableStateOf(false) }
    var adminPassword by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(attendanceState) {
        attendanceState?.let { snackbarHostState.showSnackbar(it) }
    }

    LaunchedEffect(reportResult) {
        reportResult?.let { snackbarHostState.showSnackbar(it) }
    }

    // --- DIÁLOGOS ---
    if (showAdminLogin) {
        AlertDialog(
            onDismissRequest = {
                showAdminLogin = false
                adminPassword = ""
            },
            title = { Text("Acceso Restringido") },
            text = {
                Column {
                    Text("Introduzca clave de Administrador:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = adminPassword,
                        onValueChange = { adminPassword = it },
                        label = { Text("Clave") },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = loginError != null,
                        supportingText = { loginError?.let { Text(it) } }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.loginAsAdmin(adminPassword) {
                        showAdminLogin = false
                        adminPassword = ""
                        onNavigateToReports()
                    }
                }) { Text("Entrar") }
            },
            dismissButton = {
                TextButton(onClick = { showAdminLogin = false }) { Text("Cancelar") }
            }
        )
    }

    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = { Text("Reportar Error") },
            text = {
                OutlinedTextField(
                    value = reportDescription,
                    onValueChange = { reportDescription = it },
                    label = { Text("Descripción") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.submitReport(reportDescription)
                    showReportDialog = false
                    reportDescription = ""
                }) { Text("Enviar") }
            },
            dismissButton = { TextButton(onClick = { showReportDialog = false }) { Text("Cancelar") } }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("DualClock") },
                actions = {
                    IconButton(onClick = { showReportDialog = true }) {
                        Icon(Icons.Filled.Warning, "Reportar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Navbar
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onNavigateToSettings) { Text("Config") }
                TextButton(onClick = {
                    if (isAdmin) onNavigateToReports() else showAdminLogin = true
                }) { Text("Informes") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selector Usuario
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedUser,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Trabajador") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viewModel.trabajadores.forEach { nombre ->
                        DropdownMenuItem(
                            text = { Text(nombre) },
                            onClick = { viewModel.onUserSelected(nombre); expanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Reloj
            Text(text = currentTime, style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.primary)
            Text(text = "Usuario: $selectedUser", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(32.dp))

            // Botones Reales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.checkIn() },
                    modifier = Modifier.weight(1f).height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) { Text("ENTRAR") }

                Button(
                    onClick = { viewModel.checkOut() },
                    modifier = Modifier.weight(1f).height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) { Text("SALIR") }
            }

            Spacer(modifier = Modifier.weight(1f))

            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            // --- PANEL DE SIMULACIÓN (TESTING) ---
            Text("Simulación de Datos (Test)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = { viewModel.simulatePerfectDay() }) {
                    Text("Todo OK", fontSize = 10.sp)
                }
                OutlinedButton(onClick = { viewModel.simulateLateEntry() }) {
                    Text("Llega Tarde", fontSize = 10.sp, color = Color(0xFFFF9800))
                }
                OutlinedButton(onClick = { viewModel.simulateEarlyExit() }) {
                    Text("Sale Antes", fontSize = 10.sp, color = Color(0xFFE91E63))
                }
            }
            OutlinedButton(
                onClick = { viewModel.simulateDisaster() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text("Desastre (Tarde + Antes)", fontSize = 10.sp)
            }
        }
    }
}