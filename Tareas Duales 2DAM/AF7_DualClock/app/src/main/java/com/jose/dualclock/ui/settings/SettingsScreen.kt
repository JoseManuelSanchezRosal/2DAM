package com.jose.dualclock.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val employeeName by viewModel.employeeName.collectAsState()
    val allowedSsid by viewModel.allowedSsid.collectAsState()
    val exitTimeMinutes by viewModel.exitTimeMinutes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Datos del Empleado",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = employeeName,
                onValueChange = { viewModel.saveEmployeeName(it) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Validación de Red",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = allowedSsid,
                onValueChange = { viewModel.saveAllowedSsid(it) },
                label = { Text("SSID Wi-Fi Permitido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Hora de Salida (HH:mm)",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Calculate display HH:mm
            val hours = exitTimeMinutes / 60
            val minutes = exitTimeMinutes % 60

            var hourInput by remember(hours) { mutableStateOf(hours.toString()) }
            var minuteInput by remember(minutes) { mutableStateOf(minutes.toString()) }

            // CORREGIDO: Usamos Row importado y width importado
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = hourInput,
                    onValueChange = {
                        hourInput = it
                        // Convertir a int de forma segura y guardar
                        val h = it.toIntOrNull()
                        val m = minuteInput.toIntOrNull() ?: 0
                        if (h != null && h in 0..23 && m in 0..59) {
                            viewModel.saveExitTime(h, m)
                        }
                    },
                    label = { Text("Hora") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.width(16.dp)) // AQUÍ DABA EL ERROR

                OutlinedTextField(
                    value = minuteInput,
                    onValueChange = {
                        minuteInput = it
                        val h = hourInput.toIntOrNull() ?: 0
                        val m = it.toIntOrNull()
                        if (m != null && h in 0..23 && m in 0..59) {
                            viewModel.saveExitTime(h, m)
                        }
                    },
                    label = { Text("Minuto") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("VOLVER")
            }
        }
    }
}