package EjerciciosFicheros1dam;

import java.io.*;

/*Contar palabras en un archivo
Crea un programa que cuente el número total de palabras en el archivo datos.txt y muestre el resultado en la consola.*/
public class Ej5 {
    public static void main(String[] args) {
        File archivo = new File("ficheros/src/EjerciciosFicheros1dam/datos3.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            int contador = 0;
            while ((linea = br.readLine()) != null){
                String palabras[];


            }
            System.out.println("Hay " + contador + " palabras");
        } catch (IOException e) {
            System.out.println("Error al leer archivo " + e.getMessage());
        }
    }
}