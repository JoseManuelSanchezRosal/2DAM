package org.AF5_PSP_Dual_JMSR.controller;

import org.AF5_PSP_Dual_JMSR.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CONTROLADOR REST - CUMPLE CON EL RA4 y RA5
 * Gestionamos las peticiones HTTP de forma segura y concurrente.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    // RA4.e: Uso de colecciones concurrentes (Thread-Safe)
    // CopyOnWriteArrayList evita excepciones si múltiples hilos leen y escriben a la vez.
    private final List<Producto> inventario = new CopyOnWriteArrayList<>();

    // RA4.e: Uso de variables atómicas para evitar condiciones de carrera al generar IDs.
    private final AtomicInteger contadorIds = new AtomicInteger(2);

    public ProductoController() {
        inventario.add(new Producto(1, "Portátil Developer", 1200.00));
        inventario.add(new Producto(2, "Ratón Ergonómico", 45.50));
    }

    // --- RA4: Operación LISTAR (GET) ---
    @GetMapping
    public List<Producto> listarProductos() {
        return inventario;
    }

    // --- RA4 y RA5: Operación AÑADIR (POST) ---
    @PostMapping
    public ResponseEntity<?> anadirProducto(@RequestBody Producto nuevoProducto) {
        // Validación de datos
        if (!esValido(nuevoProducto)) {
            return ResponseEntity.badRequest().body("Error: El nombre del producto no puede estar vacío.");
        }

        // Incremento atómico y seguro para entornos multihilo
        nuevoProducto.setId(contadorIds.incrementAndGet());
        inventario.add(nuevoProducto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // --- RA4 y RA5: Operación MODIFICAR (PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarProducto(@PathVariable int id, @RequestBody Producto productoEditado) {
        // Reutilizamos la validación de datos para evitar inyectar nombres vacíos al actualizar
        if (!esValido(productoEditado)) {
            return ResponseEntity.badRequest().body("Error: El nombre del producto no puede estar vacío.");
        }

        Optional<Producto> productoEncontrado = inventario.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (productoEncontrado.isPresent()) {
            Producto p = productoEncontrado.get();
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

    // --- Método auxiliar de validación ---
    private boolean esValido(Producto producto) {
        return producto.getNombre() != null && !producto.getNombre().trim().isEmpty();
    }
}