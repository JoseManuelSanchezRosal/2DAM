package RepasoFicheros;


import java.io.*;
import java.util.ArrayList;

/*Crear fichero empleado.txt con la siguiente estructura:
        Juan;25;Programador
        Ana;30;Diseñadora
Leer el fichero, crear objetos Empleado y guardarlos en un ArrayList<Empleado>.
Mostrar luego todos los empleados.*/

public class Ej4 {
    public static void main(String[] args) {
        File archivo = new File("ficheros/src/RepasoFicheros/Ejercicio4.txt");

        //tambien podemos escribir en archivo solo si no existe con >> if(!archivo.exists()) antes del TRY (validacion)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write("Juan;25;Programador");
            bw.newLine();
            bw.write("Ana;30;Disenadora");
            bw.newLine();
            bw.write("Paco;35;Arbanil");
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
        String nombre;
        int edad;
        String prof;

        String linea;
        ArrayList<Empleado> empleados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            while ((linea = br.readLine())!=null){
                String[] datos = linea.split(";");
                //(validacion) if(datos.length == 3):
                empleados.add(new Empleado(datos[0], Integer.parseInt(datos[1]), datos[2]));
            }
        }catch (IOException e){
            System.out.println("Error al escribir " + e.getMessage());
        }
        System.out.println(empleados);
    }
}