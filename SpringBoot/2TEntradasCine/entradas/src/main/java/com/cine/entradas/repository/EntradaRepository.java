package com.cine.entradas.repository;

import com.cine.entradas.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    // REQUISITO: Consulta JPQL Avanzada
    // Verifica si hay alguna entrada para esa función, fila y asiento que NO esté cancelada
    @Query("SELECT COUNT(e) > 0 FROM Entrada e " +
            "WHERE e.funcion.id = :funcionId " +
            "AND e.fila = :fila " +
            "AND e.asiento = :asiento " +
            "AND e.estado <> 'CANCELADA'")
    boolean existeEntradaOcupada(@Param("funcionId") Long funcionId,
                                 @Param("fila") int fila,
                                 @Param("asiento") int asiento);
}