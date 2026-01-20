package com.example.casasapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CasaDao {
    // Obtener todas las casas y observar cambios en tiempo real (Flow)
    @Query("SELECT * FROM casas")
    fun obtenerTodas(): Flow<List<Casa>>

    // Buscar una casa por su ID
    @Query("SELECT * FROM casas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Casa?

    // Insertar o actualizar (si ya existe, la reemplaza)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(casa: Casa)

    // Borrar una casa
    @Delete
    suspend fun borrar(casa: Casa)
}