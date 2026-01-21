package com.example.casasapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CasaDao {
    // CLAVE: Retorna un 'Flow'. Esto es reactivo: si la base de datos cambia,
    // la UI se entera y se actualiza sola automáticamente.
    @Query("SELECT * FROM casas")
    fun obtenerTodas(): Flow<List<Casa>>

    // CLAVE: 'suspend' indica que esta función se ejecutará en una Corrutina (hilo secundario),
    // para no bloquear la pantalla mientras busca.
    @Query("SELECT * FROM casas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Casa?

    // CLAVE: OnConflictStrategy.REPLACE permite usar este mismo método para
    // INSERTAR (si id=0) y ACTUALIZAR (si el id ya existe). ¡Ahorra código!
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(casa: Casa)

    @Delete
    suspend fun borrar(casa: Casa)
}