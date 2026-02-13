import java.sql.*;
import java.util.Scanner;

public class GestionBBDD {

    private static final String URL = "jdbc:sqlite:tienda.db";

    public static void main(String[] args) {
        System.out.println("--- INICIO RA2: GESTIÓN DE PRODUCTOS SQL (INTERACTIVO) ---");

        try (Connection conn = DriverManager.getConnection(URL)) {

            if (conn != null) {
                crearTablaProductos(conn);
                crearTablaUsuarios(conn);
                insertarUsuarioPorDefecto(conn);

                Scanner sc = new Scanner(System.in);

                System.out.println("--- SISTEMA DE LOGIN ---");
                boolean autenticado = false;

                while (!autenticado) {
                    System.out.print("Usuario: ");
                    String user = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = sc.nextLine();

                    autenticado = validarLogin(conn, user, pass);

                    if (!autenticado) {
                        System.out.println("[ERROR] Credenciales incorrectas. Inténtalo de nuevo.\n");
                    }
                }

                System.out.println("\n[EXITO] ¡Bienvenido al sistema!");
                int opcion = 0;

                do {
                    mostrarMenu();
                    try {
                        opcion = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        opcion = -1;
                    }

                    switch (opcion) {
                        case 1:
                            System.out.println("\n--- Nuevo Producto ---");
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("Descripción: ");
                            String desc = sc.nextLine();
                            System.out.print("Precio: ");
                            double precio = Double.parseDouble(sc.nextLine());
                            System.out.print("Stock inicial: ");
                            int stock = Integer.parseInt(sc.nextLine());
                            insertarProducto(conn, nombre, desc, precio, stock);
                            break;
                        case 2:
                            System.out.println("\n--- Listado de Productos ---");
                            listarProductos(conn);
                            break;
                        case 3:
                            System.out.println("\n--- Actualizar Stock ---");
                            System.out.print("ID del producto: ");
                            int idUpdate = Integer.parseInt(sc.nextLine());
                            System.out.print("Nuevo Stock: ");
                            int nuevoStock = Integer.parseInt(sc.nextLine());
                            actualizarStock(conn, idUpdate, nuevoStock);
                            break;
                        case 4:
                            System.out.println("\n--- Eliminar Producto ---");
                            System.out.print("ID del producto a eliminar: ");
                            int idDelete = Integer.parseInt(sc.nextLine());
                            eliminarProducto(conn, idDelete);
                            break;
                        case 5:
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

    private static void crearTablaUsuarios(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE NOT NULL, "
                + "password TEXT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void insertarUsuarioPorDefecto(Connection conn) throws SQLException {
        String sql = "INSERT OR IGNORE INTO usuarios(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "admin");
            pstmt.setString(2, "1234");
            pstmt.executeUpdate();
        }
    }

    private static boolean validarLogin(Connection conn, String username, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuarioLogueado = new Usuario(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                    return true;
                }
            }
        }
        return false;
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

    private static void listarProductos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM productos";
        try (Statement stmt = conn.createStatement();
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

    private static void simularVentaTransaccional(Connection conn) {
        System.out.println("\n--- Iniciando Transacción de Prueba ---");
        try {
            conn.setAutoCommit(false);
            insertarProducto(conn, "Teclado Transacción", "Mecánico Prueba", 50.00, 5);
            boolean errorEnProceso = false;
            if (errorEnProceso) throw new SQLException("Fallo simulado en el sistema de pago.");
            conn.commit();
            System.out.println("TRANSACCIÓN EXITOSA (COMMIT): Datos guardados permanentemente.");
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("ROLLBACK EJECUTADO: Se han deshecho los cambios por error: " + e.getMessage());
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }
}