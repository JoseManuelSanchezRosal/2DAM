package com.josemanuel.tpv.dto;

public class ProductoDTO {
    private int id;
    private String nombre;
    private double precio;
    private String imagen;
    private boolean tieneStock;

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isTieneStock() {
        return tieneStock;
    }

    public void setTieneStock(boolean tieneStock) {
        this.tieneStock = tieneStock;
    }
}