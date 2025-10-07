package RepasoFicheros2;
/*Crear fichero con la siguiente estructura para cada fila:
ID:Nombre:Edad (1:Juan:20)
Leer toda la línea y mostrar solo el nombre en cada fila.*/
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Ej1 {
    public static void main(String[] args) {
        File fichero = new File("ficheros/src/RepasoFicheros2/ej1.txt");
        try (
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))){
            bw.write("1:Juan:20\n");
            bw.write("2:Pepe:24\n");
            bw.write("3:Ana:24\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo " + e.getMessage());
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String linea;
            while ((linea = br.readLine())!=null){
                String[] palabras;
                palabras = linea.split(":");
                System.out.println(palabras[1]);
        }
        }catch (IOException e){
            System.out.println("Error al leer el archivo " + e.getMessage());
        }
    }
}