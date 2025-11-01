package Tarea1.Ej4;
//Creamos la clase empleado
public class empleado {
    private String nombre;
    private int edad;
    private String profesion;
//Constructor
    public empleado(String nombre, int edad, String profesion){
        this.nombre = nombre;
        this.edad = edad;
        this.profesion = profesion;
    }
    //ToString
    @Override
    public String toString() {
        return "empleado{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", profesion='" + profesion + '\'' +
                '}';
    }
}
