package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Representa una línea en la TableView de la comanda de una mesa.
 * Utiliza propiedades observables para la actualización automática de la tabla.
 */
public class ProductoComanda {
    
    // Propiedades observables para enlazar con la TableView
    private final SimpleStringProperty nombre;
    private final SimpleIntegerProperty cantidad;
    private final SimpleDoubleProperty subtotal;
    
    // Valor fijo que no necesita ser observable
    private final double precioUnitario; 
    
    public ProductoComanda(String nombre, int cantidadInicial, double precioUnitario) {
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleIntegerProperty(cantidadInicial);
        this.precioUnitario = precioUnitario;
        this.subtotal = new SimpleDoubleProperty(precioUnitario * cantidadInicial);
    }
    
    // --- MÉTODOS DE PROPIEDAD (CRUCIALES PARA LA TableView) ---
    // Los nombres deben coincidir con PropertyValueFactory en ComandaController.java
    
    public SimpleStringProperty nombreProperty() {
        return nombre;
    }
    
    public SimpleIntegerProperty cantidadProperty() {
        return cantidad;
    }
    
    public SimpleDoubleProperty subtotalProperty() {
        return subtotal;
    }

    // --- Lógica de Negocio y Getters ---
    
    public int getCantidad() {
        return cantidad.get();
    }
    
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    
    /**
     * Establece una nueva cantidad y recalcula automáticamente el subtotal.
     * @param newCantidad La nueva cantidad del producto.
     */
    public void setCantidad(int newCantidad) {
        if (newCantidad >= 0) {
            this.cantidad.set(newCantidad);
            // El subtotal se actualiza automáticamente al cambiar la cantidad
            this.subtotal.set(newCantidad * this.precioUnitario);
        }
    }
}