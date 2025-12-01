package com.josemanuel.tpv.repository;

import com.josemanuel.tpv.models.Categoria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoriaRepository {
    private Connection connection;

    public CategoriaRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtener todas las categorías de la base de datos
     * @return ArrayList con todas las categorías
     */
    public ArrayList<Categoria> obtener() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(
                    "SELECT id, nombre FROM Categoria"
            );
            Categoria categoria;
            while (resultSet.next()) {
                categoria = new Categoria();
                categoria.setId(resultSet.getInt(1));
                categoria.setNombre(resultSet.getString(2));
                categorias.add(categoria);
            }
            resultSet.close();
            return categorias;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorias;
    }
}