import java.sql.*;
import java.util.Scanner;

/**
 * CLASE PARA EL RESULTADO DE APRENDIZAJE 2 (RA2)
 * Gestión de información en bases de datos relacionales.
 */
public class GestionBBDD {

    // [CRITERIO RA2.b]: Utilizamos un gestor de base de datos embebido (SQLite).
    // [CRITERIO RA2.c]: Usamos el conector idóneo (cadena de conexión JDBC para SQLite).
    private static final String URL = "jdbc:sqlite:tienda.db";

    public static void main(String[] args) {
        System.out.println("--- INICIO RA2: GESTIÓN DE PRODUCTOS SQL (INTERACTIVO) ---");

        // [CRITERIO RA2.d]: Establecemos la conexión con la base de datos.
        // Utilizamos try-with-resources para asegurar el cierre de recursos.
        try (Connection conn = DriverManager.getConnection(URL)) {

            if (conn != null) {
                System.out.println("1. Conexión establecida.");

                // 1. Definición de la estructura (DDL)
                crearTablaProductos(conn);

                Scanner sc = new Scanner(System.in);
                int opcion = 0;

                // Bucle del menú interactivo
                do {
                    mostrarMenu();
                    try {
                        opcion = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        opcion = -1;
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

                            // Llamada a método de inserción
                            insertarProducto(conn, nombre, desc, precio, stock);
                            break;

                        case 2: // Listar
                            System.out.println("\n--- Listado de Productos ---");
                            // Llamada a método de consulta
                            listarProductos(conn);
                            break;

                        case 3: // Actualizar
                            System.out.println("\n--- Actualizar Stock ---");
                            System.out.print("ID del producto: ");
                            int idUpdate = Integer.parseInt(sc.nextLine());
                            System.out.print("Nuevo Stock: ");
                            int nuevoStock = Integer.parseInt(sc.nextLine());

                            // Llamada a método de actualización
                            actualizarStock(conn, idUpdate, nuevoStock);
                            break;

                        case 4: // Eliminar
                            System.out.println("\n--- Eliminar Producto ---");
                            System.out.print("ID del producto a eliminar: ");
                            int idDelete = Integer.parseInt(sc.nextLine());

                            // Llamada a método de eliminación
                            eliminarProducto(conn, idDelete);
                            break;

                        case 5: // Transacción
                            // [CRITERIO RA2.j]: Llamada a la gestión de transacciones.
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

                sc.close();
            }

        } catch (SQLException e) {
            System.err.println("Error BBDD: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: Debemos introducir un número válido. " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMENÚ DE GESTIÓN:");
        System.out.println("1. Insertar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Actualizar Stock");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Simular Transacción (Prueba integridad)");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // [CRITERIO RA2.e]: Definimos la estructura de la base de datos (CREATE TABLE).
    private static void crearTablaProductos(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "descripcion TEXT, "
                + "precio REAL, "
                + "stock INTEGER)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // [CRITERIO RA2.f]: Aplicación donde modificamos el contenido (INSERT).
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

    // [CRITERIO RA2.h]: Aplicación donde efectuamos consultas (SELECT).
    private static void listarProductos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM productos";

        // [CRITERIO RA2.g]: Definimos objetos para almacenar el resultado (ResultSet).
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                // Acceso a los datos almacenados en el ResultSet
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

    // [CRITERIO RA2.f]: Aplicación donde modificamos el contenido (UPDATE).
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

    // [CRITERIO RA2.f]: Aplicación donde modificamos el contenido (DELETE).
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

    // [CRITERIO RA2.j]: Gestionamos las transacciones e integridad (COMMIT / ROLLBACK).
    private static void simularVentaTransaccional(Connection conn) {
        System.out.println("\n--- Iniciando Transacción de Prueba ---");
        try {
            // 1. Inicio de la transacción: Desactivamos el autocommit
            conn.setAutoCommit(false);

            // Operación: Insertamos un producto de prueba
            insertarProducto(conn, "Teclado Transacción", "Mecánico Prueba", 50.00, 5);

            // Simulación de error lógico para probar la integridad
            boolean errorEnProceso = false; // Cambiar a 'true' para forzar el ROLLBACK

            if (errorEnProceso) throw new SQLException("Fallo simulado en el sistema de pago.");

            // 2. Confirmación de la transacción (Si todo va bien)
            conn.commit();
            System.out.println("TRANSACCIÓN EXITOSA (COMMIT): Datos guardados permanentemente.");

        } catch (SQLException e) {
            try {
                // 3. Reversión de la transacción (Si algo falla)
                // Esto asegura la consistencia de los datos
                conn.rollback();
                System.out.println("ROLLBACK EJECUTADO: Se han deshecho los cambios por error: " + e.getMessage());
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try {
                // Restauramos el comportamiento por defecto
                conn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }
}