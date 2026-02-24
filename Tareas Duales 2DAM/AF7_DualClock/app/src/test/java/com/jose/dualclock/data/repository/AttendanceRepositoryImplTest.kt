package com.jose.dualclock.data.repository

import com.jose.dualclock.data.local.datastore.SettingsDataStore
import com.jose.dualclock.data.local.room.AppDatabase
import com.jose.dualclock.data.local.room.AttendanceDao
import com.jose.dualclock.data.local.room.AttendanceEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AttendanceRepositoryImplTest {

    private lateinit var repository: AttendanceRepositoryImpl
    private lateinit var attendanceDao: AttendanceDao
    private lateinit var settingsDataStore: SettingsDataStore
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        // 1. Simular (mock) tanto el DAO como la base de datos completa
        attendanceDao = mock()
        appDatabase = mock()
        settingsDataStore = mock()

        // 2. Configurar el mock de la base de datos para que devuelva el DAO simulado
        whenever(appDatabase.attendanceDao()).thenReturn(attendanceDao)

        // 3. Crear el repositorio pasándole el mock de AppDatabase, que es lo que ahora espera
        repository = AttendanceRepositoryImpl(appDatabase, settingsDataStore)
    }

    @Test
    fun checkIn_success_insertsCorrectEntity() = runTest {
        // Given
        whenever(settingsDataStore.employeeName).thenReturn(flowOf("TestUser"))

        // When
        val result = repository.checkIn()

        // Then
        assertTrue(result.isSuccess)
        val captor = argumentCaptor<AttendanceEntity>()
        verify(attendanceDao).insertAttendance(captor.capture())
        val capturedEntity = captor.firstValue
        assertEquals("TestUser", capturedEntity.userId)
        assertEquals("IN", capturedEntity.type)
        assertEquals(false, capturedEntity.isSynced)
    }

    @Test
    fun checkOut_success_insertsCorrectEntity() = runTest {
        // Given
        whenever(settingsDataStore.employeeName).thenReturn(flowOf("TestUser"))

        // When
        val result = repository.checkOut()

        // Then
        assertTrue(result.isSuccess)
        val captor = argumentCaptor<AttendanceEntity>()
        verify(attendanceDao).insertAttendance(captor.capture())
        val capturedEntity = captor.firstValue
        assertEquals("TestUser", capturedEntity.userId)
        assertEquals("OUT", capturedEntity.type)
    }

    @Test
    fun getAttendanceForMonth_callsDaoWithCorrectRange() = runTest {
        // Given
        val month = 2 // February
        val year = 2024
        val expectedStart = java.util.Calendar.getInstance().apply {
            set(2024, 1, 1, 0, 0, 0) // Month is 0-indexed in Calendar
            set(java.util.Calendar.MILLISECOND, 0)
        }.timeInMillis
        
        val expectedEnd = java.util.Calendar.getInstance().apply {
            set(2024, 2, 1, 0, 0, 0)
            set(java.util.Calendar.MILLISECOND, 0)
            add(java.util.Calendar.MILLISECOND, -1)
        }.timeInMillis

        whenever(attendanceDao.getAttendanceBetween(any(), any())).thenReturn(flowOf(emptyList()))

        // When
        repository.getAttendanceForMonth(month, year)

        // Then
        verify(attendanceDao).getAttendanceBetween(expectedStart, expectedEnd)
    }
}
