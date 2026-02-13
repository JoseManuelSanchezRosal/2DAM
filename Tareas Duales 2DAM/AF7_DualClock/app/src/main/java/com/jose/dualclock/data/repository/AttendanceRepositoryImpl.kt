package com.jose.dualclock.data.repository

import com.jose.dualclock.data.local.datastore.SettingsDataStore
import com.jose.dualclock.data.local.room.AppDatabase
import com.jose.dualclock.data.local.room.AttendanceEntity
import com.jose.dualclock.data.local.room.ReportEntity
import com.jose.dualclock.domain.repository.AttendanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val settingsDataStore: SettingsDataStore
) : AttendanceRepository {

    private val attendanceDao = db.attendanceDao()
    private val reportDao = db.reportDao()

    override fun getAllAttendance() = attendanceDao.getAllAttendance()

    override suspend fun checkIn(): Result<Long> = saveRecord("IN", System.currentTimeMillis())
    override suspend fun checkOut(): Result<Long> = saveRecord("OUT", System.currentTimeMillis())

    override suspend fun simulateAttendance(timestamp: Long, type: String): Result<Long> {
        return saveRecord(type, timestamp)
    }

    private suspend fun saveRecord(type: String, timestamp: Long): Result<Long> {
        return withContext(Dispatchers.IO) {
            try {
                val employeeName = settingsDataStore.employeeName.first()
                val entity = AttendanceEntity(userId = employeeName, timestamp = timestamp, type = type, isSynced = false)
                attendanceDao.insertAttendance(entity)
                Result.success(timestamp)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // MODIFICADO: Usamos el userName que nos pasan por parámetro
    override suspend fun reportIssue(userName: String, description: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val report = ReportEntity(
                    userId = userName, // <--- AQUÍ GUARDAMOS EL NOMBRE CORRECTO
                    description = description,
                    timestamp = System.currentTimeMillis()
                )
                reportDao.insertReport(report)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override fun getAllReports(): Flow<List<ReportEntity>> = reportDao.getAllReports()

    override suspend fun resolveReport(id: Long) {
        withContext(Dispatchers.IO) {
            reportDao.deleteReport(id)
        }
    }

    override suspend fun syncData(): Result<Boolean> { return Result.success(true) }
    override fun getAttendanceForMonth(month: Int, year: Int): Flow<List<AttendanceEntity>> = attendanceDao.getAllAttendance()
}