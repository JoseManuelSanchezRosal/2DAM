package EjerciciosFicheros1dam;

import java.io.*;

/*Leer un archivo línea por línea
Desarrolla un programa en Java que lea el archivo datos.txt línea por línea e imprima cada línea en la consola.*/
public class Ej3 {
    public static void main(String[] args) {
        File archivo = new File("ficheros/src/EjerciciosFicheros1dam/datos3.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null){
                System.out.println(linea);
            }
        }catch (IOException e){
            System.out.println("Error al escribir en archivo " + e.getMessage());
        }
    }
}
