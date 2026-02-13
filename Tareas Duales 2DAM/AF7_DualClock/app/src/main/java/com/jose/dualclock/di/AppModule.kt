package com.jose.dualclock.di

import android.content.Context
import androidx.room.Room
import com.jose.dualclock.data.local.datastore.SettingsDataStore
import com.jose.dualclock.data.local.room.AppDatabase
import com.jose.dualclock.data.local.room.AttendanceDao
import com.jose.dualclock.data.local.room.ReportDao
import com.jose.dualclock.data.repository.AttendanceRepositoryImpl
import com.jose.dualclock.domain.repository.AttendanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dual_clock_db"
        )
            .fallbackToDestructiveMigration() // Importante: Borra la BBDD vieja si cambias la estructura
            .build()
    }

    @Provides
    @Singleton
    fun provideAttendanceDao(appDatabase: AppDatabase): AttendanceDao {
        return appDatabase.attendanceDao()
    }

    // Nuevo: Proveemos también el DAO de Reportes
    @Provides
    @Singleton
    fun provideReportDao(appDatabase: AppDatabase): ReportDao {
        return appDatabase.reportDao()
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }

    // --- CORRECCIÓN AQUÍ ---
    @Provides
    @Singleton
    fun provideAttendanceRepository(
        db: AppDatabase, // Antes era 'attendanceDao: AttendanceDao'
        settingsDataStore: SettingsDataStore
    ): AttendanceRepository {
        // Ahora pasamos la base de datos completa, como pide el nuevo constructor
        return AttendanceRepositoryImpl(db, settingsDataStore)
    }
}