package RepasoFicheros2;

import java.io.*;
import java.util.ArrayList;

/*Crear fichero productos.txt
    1;Teclado;25.5
    2;Raton;15.0
    3;Monitor;200.0
Leer todos los productos en memoria, aumentar todos los precios un 10% y
guardarlos en productos_actualizados.txt*/
public class Ej5 {
    public static void main(String[] args) {
        File fichero = new File("ficheros/src/RepasoFicheros2/ej5.txt");
        ArrayList<String> productos = new ArrayList<>();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            bw.write("1;Teclado;25.5\n");
            bw.write("2;Raton;15.0\n");
            bw.write("3;Monitor;200\n");
            bw.close();
        }catch (IOException e){
            System.out.println("Error al escribir en el archivo " + e.getMessage());
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String linea;
            while ((linea = br.readLine())!=null){
                String[] palabra = linea.split(";");
                double precio = Double.parseDouble(palabra[2]);
                double precioAumentado = precio * 1.1;
                System.out.println(precioAumentado);

            }
        }catch (IOException e){
            System.out.println("Error al leer el archivo " + e.getMessage());
        }
    }
}
