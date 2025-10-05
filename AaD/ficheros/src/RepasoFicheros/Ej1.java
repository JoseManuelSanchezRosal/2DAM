package RepasoFicheros;
import java.io.*;
// Ejercicio 1: Crear un fichero con la estructura para cada fila:
//  - ID:Nombre:Edad: (1:Juan:20)...
//  - Leer toda la linea y mostrar solo el nombre en cada fila
public class Ej1 {
    public static void main(String[] args) {
        // Primero creamos el fichero y escribimos algunas lineas con esa estructura:
        File archivo = new File("ficheros/src/RepasoFicheros/Ejercicio1.txt");

        // SI METEMOS EL BR O EL BR ENTRE PARENTESIS TRAS EL TRY, SE LLAMA TRY-WITH-RESOURCES. SIRVE PARA NO TENER QUE PONER TRY-WITH-RESOURCES
        // Escribimos:
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write("1:Juan:20");
            bw.newLine();
            bw.write("2:Pepe:25");
            bw.newLine();
            bw.write("3:Lucia:30");
            bw.newLine();

        // PONER IOException para flujos de entrada y salida de datos.
        }catch (IOException e){
            System.out.println("Error al escribir en el archivo" + e.getMessage());
        }
        // Leemos:
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = br.readLine()) !=null){
                String[] palabras;
                //OPCION 1:
//                palabras = linea.split(":");
//                System.out.println(palabras[1]);

                System.out.println(linea.split(":")[1]);//OPCION 2 (SIMPLIFICADA)
            }
        }catch (IOException e){
            System.out.println("Error al leer del archivo " + e.getMessage());
        }
    }
}