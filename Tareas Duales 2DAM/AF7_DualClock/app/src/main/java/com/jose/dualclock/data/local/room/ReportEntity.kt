package com.jose.dualclock.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_table")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,        // Quién reporta
    val description: String,   // Qué pasa
    val timestamp: Long        // Cuándo pasó
)