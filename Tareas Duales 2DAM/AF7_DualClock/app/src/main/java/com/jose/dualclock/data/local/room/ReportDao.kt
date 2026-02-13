package com.jose.dualclock.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Insert
    suspend fun insertReport(report: ReportEntity)

    @Query("SELECT * FROM report_table ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    @Query("DELETE FROM report_table WHERE id = :id")
    suspend fun deleteReport(id: Long)
}