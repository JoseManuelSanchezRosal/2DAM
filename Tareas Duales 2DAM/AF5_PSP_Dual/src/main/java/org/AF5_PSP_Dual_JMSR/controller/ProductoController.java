package org.AF5_PSP_Dual_JMSR.controller;

import org.AF5_PSP_Dual_JMSR.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CONTROLADOR REST - CUMPLE CON EL RA4
 * Gestionamos las peticiones HTTP (GET, POST, PUT, DELETE).
 */
@RestController
@RequestMapping("/api/productos") // Ruta base: http://localhost:8080/api/productos
public class ProductoController {

    // RA4.d: Simulamos una base de datos en memoria.
    private List<Producto> inventario = new ArrayList<>();

    // CORRECCIÓN: Usamos un contador independiente para los IDs.
    // Empezamos en 2 porque ya tenemos el ID 1 y 2 creados en el constructor.
    private int contadorIds = 2;

    public ProductoController() {
        // Inicializamos con algunos datos de prueba
        inventario.add(new Producto(1, "Portátil Developer", 1200.00));
        inventario.add(new Producto(2, "Ratón Ergonómico", 45.50));
    }

    // --- RA4: Operación LISTAR (GET) ---
    @GetMapping
    public List<Producto> listarProductos() {
        return inventario;
    }

    // --- RA4: Operación AÑADIR (POST) ---
    @PostMapping
    public ResponseEntity<?> anadirProducto(@RequestBody Producto nuevoProducto) {
        // RA5: Validación básica de datos (Seguridad)
        if (nuevoProducto.getNombre() == null || nuevoProducto.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El nombre del producto no puede estar vacío.");
        }

        // CORRECCIÓN: Autoincrement real.
        // Sumamos 1 al contador global, sin importar el tamaño de la lista.
        contadorIds++;
        nuevoProducto.setId(contadorIds);

        inventario.add(nuevoProducto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // --- RA4: Operación MODIFICAR (PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarProducto(@PathVariable int id, @RequestBody Producto productoEditado) {
        // Buscamos el producto por ID
        Optional<Producto> productoEncontrado = inventario.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (productoEncontrado.isPresent()) {
            Producto p = productoEncontrado.get();
            // Actualizamos los campos
            p.setNombre(productoEditado.getNombre());
            p.setPrecio(productoEditado.getPrecio());
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    // --- RA4: Operación ELIMINAR (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        boolean eliminado = inventario.removeIf(p -> p.getId() == id);
        if (eliminado) {
            return ResponseEntity.ok("Producto eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar: ID no existe.");
        }
    }
}