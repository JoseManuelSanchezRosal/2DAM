package com.jose.dualclock.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Attendance entities.
 */
@Dao
interface AttendanceDao {

    /**
     * Inserts an attendance record.
     * @return The row ID of the inserted record.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity): Long

    /**
     * Returns all attendance records, ordered by timestamp descending.
     */
    @Query("SELECT * FROM attendance_table ORDER BY timestamp DESC")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    /**
     * Returns all records that have not yet been synced.
     */
    @Query("SELECT * FROM attendance_table WHERE isSynced = 0")
    suspend fun getUnsyncedAttendance(): List<AttendanceEntity>

    /**
     * Marks a record as synced.
     */
    @Query("UPDATE attendance_table SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)

    /**
     * Returns records falling within the given timestamp range.
     */
    @Query("SELECT * FROM attendance_table WHERE timestamp >= :startTime AND timestamp <= :endTime")
    fun getAttendanceBetween(startTime: Long, endTime: Long): Flow<List<AttendanceEntity>>
}