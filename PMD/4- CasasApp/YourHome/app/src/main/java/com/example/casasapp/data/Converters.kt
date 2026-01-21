package com.example.casasapp.data

import androidx.room.TypeConverter

// CLAVE: Room solo entiende tipos básicos (Texto, Número).
// Esta clase convierte tu Lista de Fotos ["img1.jpg", "img2.jpg"] a un solo Texto "img1.jpg||img2.jpg"
// para poder guardarlo, y viceversa al leerlo.
class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = "||")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        return value.split("||")
    }
}