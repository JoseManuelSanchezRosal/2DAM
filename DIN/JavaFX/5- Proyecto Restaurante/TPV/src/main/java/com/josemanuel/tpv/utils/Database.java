package com.josemanuel.tpv.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Database database;
    private Connection connection;

    private Database() {
        Dotenv dotenv = Dotenv.load();

        try {
            // Variables de entorno para cargar las credenciales de la base de datos en tiempo de ejecución y así evitar filtraciones de contraseñas
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + dotenv.get("DATABASE_HOST") + ":" + dotenv.get("DATABASE_PORT") + "/" + dotenv.get("DATABASE_NAME"), dotenv.get("DATABASE_USER"), dotenv.get("DATABASE_PASSWORD")
            );
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar a la base de datos");
        }
    }

    public static Database createConnection() {
        // Patrón Singleton para tener una sola conexión a la base de datos en toda la aplicación
        if (Database.database == null) {
            Database.database = new Database();
        }
        return Database.database;
    }

    public Connection getConnection() {
        return connection;
    }
}