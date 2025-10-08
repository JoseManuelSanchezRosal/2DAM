package EjerciciosFicheros1dam;

import java.io.*;

/*Agregar contenido a un archivo
Modifica el archivo datos.txt para agregar nuevas líneas de texto sin borrar el contenido anterior.*/
public class Ej4 {
    public static void main(String[] args) {
        File arhivo = new File("ficheros/src/EjerciciosFicheros1dam/datos1.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arhivo, true));
            bw.write("Hola este es un mensaje agregado desde el ej4\n");
            bw.write("Hola este es otro mensaje agregado desde el ej4\n");

            bw.close();
        }catch (IOException e){
            System.out.println("Error al escribir en archivo " + e.getMessage());
        }
        System.out.println("Mensajes agregados al archivo " + arhivo.getName());
    }
}
