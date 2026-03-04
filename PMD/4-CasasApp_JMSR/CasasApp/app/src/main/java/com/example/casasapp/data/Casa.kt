package com.example.casasapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// CLAVE: @Entity marca esta clase como una tabla en la base de datos SQLite.
@Entity(tableName = "casas")
data class Casa(
    // CLAVE: @PrimaryKey(autoGenerate = true) hace que el ID se incremente solo (1, 2, 3...).
    // Es vital para diferenciar registros.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val imagenes: List<String>, // NOTA: Room no guarda listas por defecto, usa el Converter.

    // --- NUEVOS CAMPOS ---
    val precio: Double,
    val esAlquiler: Boolean,  // true = Alquiler (Azul), false = Venta (Naranja)
    val direccion: String,
    val extras: List<String>
)