package RepasoFicheros2;

import java.io.*;
import java.util.ArrayList;

/*Crear fichero empleado.txt con la siguiente estructura:
    Juan;25;Programador
    Ana;30;Diseñadora
Leer el fichero, crear objetos Empleado y guardarlos en un ArrayList<Empleado>.
Mostrar luego todos los empleados.*/
public class Ej4 {
    public static void main(String[] args) {
        File archivo = new File("ficheros/src/RepasoFicheros2/ej3.txt");
        ArrayList<Empleado> empleados = new ArrayList<>();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Juan;25;Programador\n");
            bw.write("Ana;35;Disenadora\n");
            bw.close();
        }catch (IOException e){
            System.out.println("Error al escribir en archivo " + e.getMessage());
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine())!=null){
                String[] palabras;
                palabras = linea.split(";");
                Empleado e = new Empleado(palabras[0], Integer.parseInt(palabras[1]), palabras[2]);
                empleados.add(e);
            }
            System.out.println("Lista de empleados");
            System.out.println("----------------------------------------------------------------");
            for (Empleado e: empleados){
                System.out.println(e);
            }
        }catch (IOException e){
            System.out.println("Error al leer el archivo " + e.getMessage());
        }
    }
}