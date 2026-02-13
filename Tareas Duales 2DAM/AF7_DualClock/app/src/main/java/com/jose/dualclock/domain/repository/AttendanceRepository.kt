package com.jose.dualclock.domain.repository

import com.jose.dualclock.data.local.room.AttendanceEntity
import com.jose.dualclock.data.local.room.ReportEntity
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getAllAttendance(): Flow<List<AttendanceEntity>>
    suspend fun checkIn(): Result<Long>
    suspend fun checkOut(): Result<Long>
    suspend fun syncData(): Result<Boolean>
    fun getAttendanceForMonth(month: Int, year: Int): Flow<List<AttendanceEntity>>

    // MODIFICADO: Ahora pedimos el nombre (userName) explícitamente
    suspend fun reportIssue(userName: String, description: String): Result<Boolean>

    suspend fun simulateAttendance(timestamp: Long, type: String): Result<Long>

    fun getAllReports(): Flow<List<ReportEntity>>
    suspend fun resolveReport(id: Long)
}