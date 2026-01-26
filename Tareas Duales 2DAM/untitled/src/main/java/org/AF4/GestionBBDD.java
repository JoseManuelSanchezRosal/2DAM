package org.AF4;

import java.sql.*;
import java.util.Scanner; // Importamos Scanner para leer del teclado

/**
 * CLASE PARA EL RESULTADO DE APRENDIZAJE 2 (RA2)
 * Gestión de información en bases de datos relacionales.
 * MODIFICADO: Incluimos menú interactivo por consola.
 */
public class GestionBBDD {

    // Criterio RA2.c.: Usamos el conector idóneo (JDBC para SQLite).
    // Criterio RA2.b.: Utilizamos un gestor de base de datos embebido (SQLite).
    private static final String URL = "jdbc:sqlite:tienda.db";

    public static void main(String[] args) {
        System.out.println("--- INICIO RA2: GESTIÓN DE PRODUCTOS SQL (INTERACTIVO) ---");

        // Criterio RA2.d.: Establecemos la conexión.
        // Mantenemos la conexión abierta durante la ejecución del menú usando try-with-resources
        try (Connection conn = DriverManager.getConnection(URL)) {

            if (conn != null) {
                System.out.println("1. Conexión establecida.");

                // 1. Estructura (La ejecutamos siempre al inicio para asegurar que existe la tabla)
                crearTablaProductos(conn);

                // Inicializamos Scanner para el menú
                Scanner sc = new Scanner(System.in);
                int opcion = 0;

                do {
                    mostrarMenu();
                    try {
                        opcion = Integer.parseInt(sc.nextLine()); // Leemos como String y convertimos para evitar errores de buffer
                    } catch (NumberFormatException e) {
                        opcion = -1; // Marcamos opción inválida si no escribimos un número
                    }

                    switch (opcion) {
                        case 1: // Insertar
                            System.out.println("\n--- Nuevo Producto ---");
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("Descripción: ");
                            String desc = sc.nextLine();

                            System.out.print("Precio: ");
                            double precio = Double.parseDouble(sc.nextLine());

                            System.out.print("Stock inicial: ");
                            int stock = Integer.parseInt(sc.nextLine());

                            // Criterio RA2.f.: (Realizamos la Inserción)
                            insertarProducto(conn, nombre, desc, precio, stock);
                            break;

                        case 2: // Listar
                            System.out.println("\n--- Listado de Productos ---");
                            // Criterio RA2.h (Efectuamos consultas)
                            listarProductos(conn);
                            break;

                        case 3: // Actualizar Stock
                            System.out.println("\n--- Actualizar Stock ---");
                            System.out.print("ID del producto: ");
                            int idUpdate = Integer.parseInt(sc.nextLine());
                            System.out.print("Nuevo Stock: ");
                            int nuevoStock = Integer.parseInt(sc.nextLine());

                            // Criterio RA2.f.: (Realizamos la Modificación)
                            actualizarStock(conn, idUpdate, nuevoStock);
                            break;

                        case 4: // Eliminar
                            System.out.println("\n--- Eliminar Producto ---");
                            System.out.print("ID del producto a eliminar: ");
                            int idDelete = Integer.parseInt(sc.nextLine());

                            // Criterio RA2.f.: (Realizamos la Eliminación)
                            eliminarProducto(conn, idDelete);
                            break;

                        case 5: // Transacción de prueba
                            // Criterio RA2.j (Gestionamos transacciones)
                            simularVentaTransaccional(conn);
                            break;

                        case 6:
                            System.out.println("Saliendo del sistema...");
                            break;

                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                    System.out.println("------------------------------------------------");

                } while (opcion != 6);

                sc.close(); // Cerramos el scanner al terminar
            }

        } catch (SQLException e) {
            System.err.println("Error BBDD: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: Debemos introducir un número válido. " + e.getMessage());
        }
    }

    // Método auxiliar para mostrar el menú
    private static void mostrarMenu() {
        System.out.println("\nMENÚ DE GESTIÓN:");
        System.out.println("1. Insertar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Actualizar Stock");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Simular Transacción (Prueba automática)");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Criterio RA2.e.: Definimos la estructura de la base de datos.
    private static void crearTablaProductos(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "descripcion TEXT, "
                + "precio REAL, "
                + "stock INTEGER)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            // Solo mostramos mensaje si no existía, para no saturar el menú
            // System.out.println("2. Tabla 'productos' verificada.");
        }
    }

    // Criterio RA2.f.: Aplicación donde modificamos el contenido (INSERT).
    private static void insertarProducto(Connection conn, String nom, String desc, double precio, int stock) throws SQLException {
        String sql = "INSERT INTO productos(nombre, descripcion, precio, stock) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, desc);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, stock);
            pstmt.executeUpdate();
            System.out.println(" -> [OK] Producto insertado: " + nom);
        }
    }

    // Criterio RA2.h: Aplicación donde efectuamos consultas.
    private static void listarProductos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM productos";
        try (Statement stmt = conn.createStatement();
             // Criterio RA2.g.: Definimos objetos para almacenar el resultado (ResultSet).
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                System.out.printf("ID: %d | %s | %s | %.2f€ | Stock: %d%n",
                        rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("descripcion"), rs.getDouble("precio"),
                        rs.getInt("stock"));
            }
            if (!hayDatos) {
                System.out.println("(No hay productos registrados)");
            }
        }
    }

    // Criterio RA2.f: Modificamos contenido (UPDATE).
    private static void actualizarStock(Connection conn, int id, int nuevoStock) throws SQLException {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevoStock);
            pstmt.setInt(2, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println(" -> [OK] Stock actualizado para ID " + id);
            } else {
                System.out.println(" -> [ERROR] No encontramos el producto con ID " + id);
            }
        }
    }

    // Criterio RA2.f: Modificamos contenido (DELETE).
    private static void eliminarProducto(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println(" -> [OK] Producto eliminado ID " + id);
            } else {
                System.out.println(" -> [ERROR] No encontramos el producto con ID " + id);
            }
        }
    }

    // Criterio RA2.j.: Gestionamos las transacciones (COMMIT / ROLLBACK).
    private static void simularVentaTransaccional(Connection conn) {
        System.out.println("\n--- Iniciando Transacción de Prueba ---");
        try {
            conn.setAutoCommit(false); // 1. Desactivamos el autocommit

            // Operación 1: Insertamos venta
            insertarProducto(conn, "Teclado Transacción", "Mecánico Prueba", 50.00, 5);

            // Simulación de error
            boolean errorEnProceso = false; // Cambiamos a true para probar el Rollback

            if (errorEnProceso) throw new SQLException("Fallo simulado en el sistema de pago.");

            conn.commit(); // 2. Confirmamos cambios si todo va bien
            System.out.println("TRANSACCIÓN EXITOSA: Datos guardados.");

        } catch (SQLException e) {
            try {
                conn.rollback(); // 3. Deshacemos cambios si hay error
                System.out.println("ROLLBACK: Hemos deshecho los cambios por error: " + e.getMessage());
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) {}
        }
    }
}