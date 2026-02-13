package com.jose.dualclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint

// --- IMPORTS QUE FALTABAN ---
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Imports de tus pantallas
import com.jose.dualclock.ui.MainScreen
import com.jose.dualclock.ui.theme.DualClockTheme
import com.jose.dualclock.ui.navigation.Screen
import com.jose.dualclock.ui.settings.SettingsScreen
import com.jose.dualclock.ui.reports.ReportsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DualClockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Ahora usamos rememberNavController directamente gracias al import
                    val navController = rememberNavController()

                    // NavHost directamente (sin el androidx.navigation...)
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Main.route
                    ) {
                        // Al tener el import, 'composable' ya se reconoce correctamente
                        composable(Screen.Main.route) {
                            MainScreen(
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                                onNavigateToReports = { navController.navigate(Screen.Reports.route) }
                            )
                        }

                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.Reports.route) {
                            ReportsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}