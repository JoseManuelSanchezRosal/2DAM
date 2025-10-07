package RepasoFicheros2;

import java.util.SplittableRandom;

public class Empleado {
    private String nombre;
    private int edad;
    private String profesion;

    public Empleado(String nombre, int edad, String profesion) {
        this.nombre = nombre;
        this.edad = edad;
        this.profesion = profesion;
    }
    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", profesion='" + profesion + '\'' +
                '}';
    }
}
