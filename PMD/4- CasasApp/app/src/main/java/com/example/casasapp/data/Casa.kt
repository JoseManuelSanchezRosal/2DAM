package com.example.casasapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "casas")
data class Casa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val imagenes: List<String>,

    // --- NUEVOS CAMPOS ---
    val precio: Double,       // Ej: 1200.0 o 250000.0
    val esAlquiler: Boolean,  // true = Alquiler, false = Venta
    val direccion: String,    // Ej: "C/ Mayor 12, 3ºA"
    val extras: List<String>  // Reutilizamos tu converter para guardar: ["Piscina", "Garaje"]
)