package com.jose.dualclock.domain.model

/**
 * Modelo de datos para las estadísticas del empleado.
 */
data class UserStats(
    val totalHours: Double = 0.0,
    val punctualityRate: Int = 100, // % de puntualidad (0-100)
    val lateEntries: Int = 0,       // Veces que entró tarde (> 08:05)
    val earlyExits: Int = 0         // Veces que salió antes (< 16:00)
)