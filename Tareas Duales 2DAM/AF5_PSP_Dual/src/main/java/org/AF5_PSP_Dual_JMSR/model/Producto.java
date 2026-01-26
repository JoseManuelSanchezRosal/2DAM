package org.AF5_PSP_Dual_JMSR.model;

public class Producto {

    private int id;
    private String nombre;
    private double precio;

    // 1. Constructor VACÍO (Obligatorio para que funcione el JSON)
    public Producto() {
    }

    // 2. Constructor con argumentos (Para usarlo nosotros)
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // 3. Getters y Setters (Obligatorios)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}