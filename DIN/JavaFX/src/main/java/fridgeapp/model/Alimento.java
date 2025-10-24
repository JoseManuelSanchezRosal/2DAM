package fridgeapp.model;

import javafx.beans.property.*;

public class Alimento {
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty tipo = new SimpleStringProperty();
    private final DoubleProperty cantidad = new SimpleDoubleProperty();
    private final StringProperty unidad = new SimpleStringProperty();

    public Alimento() {}

    public Alimento(String nombre, String tipo, double cantidad, String unidad) {
        this.nombre.set(nombre);
        this.tipo.set(tipo);
        this.cantidad.set(cantidad);
        this.unidad.set(unidad);
    }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getTipo() { return tipo.get(); }
    public void setTipo(String tipo) { this.tipo.set(tipo); }
    public StringProperty tipoProperty() { return tipo; }

    public double getCantidad() { return cantidad.get(); }
    public void setCantidad(double cantidad) { this.cantidad.set(cantidad); }
    public DoubleProperty cantidadProperty() { return cantidad; }

    public String getUnidad() { return unidad.get(); }
    public void setUnidad(String unidad) { this.unidad.set(unidad); }
    public StringProperty unidadProperty() { return unidad; }
}
