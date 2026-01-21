package com.example.casasapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// CLAVE: Aquí registramos las entidades y la versión. Si cambias la estructura de Casa,
// debes subir la versión.
@Database(entities = [Casa::class], version = 2)
@TypeConverters(Converters::class) // CLAVE: Vinculamos el conversor de listas.
abstract class CasaDatabase : RoomDatabase() {
    abstract fun casaDao(): CasaDao
}