package com.jose.dualclock.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_table")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val timestamp: Long,
    val type: String, // "IN" or "OUT"
    val isSynced: Boolean = false
)
