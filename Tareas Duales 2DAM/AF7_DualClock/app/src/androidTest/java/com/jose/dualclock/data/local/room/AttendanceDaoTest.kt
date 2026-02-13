package com.jose.dualclock.data.local.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AttendanceDaoTest {

    private lateinit var attendanceDao: AttendanceDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        attendanceDao = db.attendanceDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetAttendance() = runBlocking {
        val attendance = AttendanceEntity(userId = "User1", timestamp = 1000L, type = "IN")
        attendanceDao.insertAttendance(attendance)
        val allAttendance = attendanceDao.getAllAttendance().first()
        assertEquals(allAttendance[0].userId, "User1")
    }

    @Test
    fun getUnsyncedAttendance() = runBlocking {
        val synced = AttendanceEntity(userId = "User1", timestamp = 1000L, type = "IN", isSynced = true)
        val unsynced = AttendanceEntity(userId = "User1", timestamp = 2000L, type = "OUT", isSynced = false)
        attendanceDao.insertAttendance(synced)
        attendanceDao.insertAttendance(unsynced)

        val unsyncedList = attendanceDao.getUnsyncedAttendance()
        assertEquals(1, unsyncedList.size)
        assertEquals(unsynced.timestamp, unsyncedList[0].timestamp)
    }

    @Test
    fun markAsSynced() = runBlocking {
        val attendance = AttendanceEntity(userId = "User1", timestamp = 1000L, type = "IN", isSynced = false)
        val id = attendanceDao.insertAttendance(attendance)

        attendanceDao.markAsSynced(id)
        val unsyncedList = attendanceDao.getUnsyncedAttendance()
        assertTrue(unsyncedList.isEmpty())
    }

    @Test
    fun getAttendanceBetween() = runBlocking {
        val attendance1 = AttendanceEntity(userId = "User1", timestamp = 1000L, type = "IN")
        val attendance2 = AttendanceEntity(userId = "User1", timestamp = 2000L, type = "OUT")
        val attendance3 = AttendanceEntity(userId = "User1", timestamp = 3000L, type = "IN")

        attendanceDao.insertAttendance(attendance1)
        attendanceDao.insertAttendance(attendance2)
        attendanceDao.insertAttendance(attendance3)

        val rangeList = attendanceDao.getAttendanceBetween(1500L, 2500L).first()
        assertEquals(1, rangeList.size)
        assertEquals(attendance2.timestamp, rangeList[0].timestamp)
    }
}
