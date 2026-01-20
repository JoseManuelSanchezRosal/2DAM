package com.example.casasapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Casa::class], version = 2)
@TypeConverters(Converters::class) // <-- Añade esto
abstract class CasaDatabase : RoomDatabase() {
    abstract fun casaDao(): CasaDao
}