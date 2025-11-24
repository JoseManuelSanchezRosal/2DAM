package model;

/**
 * Representa un producto estático del catálogo o menú.
 */
public class Producto {
    
    private final String nombre;
    private final double precio;
    private final String imagenUrl; // Ejemplo: "/sources/AGUA.jpg"

    public Producto(String nombre, double precio, String imagenUrl) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
    }

    // --- Getters ---
    
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }
}