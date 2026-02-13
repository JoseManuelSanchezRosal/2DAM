package com.jose.dualclock.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AttendanceEntity::class, ReportEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDao
    abstract fun reportDao(): ReportDao
}