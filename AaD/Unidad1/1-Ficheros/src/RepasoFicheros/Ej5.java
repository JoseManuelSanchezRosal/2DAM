package RepasoFicheros;


/*Crear fichero productos.txt
        1;Teclado;25.5
        2;Raton;15.0
        3;Monitor;200.0
Leer todos los productos en memoria, aumentar todos los precios un 10% y
guardarlos en productos_actualizados.txt*/

import java.io.*;
import java.util.ArrayList;

public class Ej5 {
    public static void main(String[] args) {
        // Creamos la ruta del archivo y anadimos los productos.

        File origen = new File("ficheros/src/RepasoFicheros/Ejercicio5.txt");
        File precioAumentado = new File("ficheros/src/RepasoFicheros/PrecioAumentado.txt");
        // Escribimos con bw los productos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(origen))){
            bw.write("1;Teclado;25.5");
            bw.newLine();
            bw.write("2;Raton;15.0");
            bw.newLine();
            bw.write("3;Monitor;200");
            bw.newLine();
        }catch (IOException e){
            System.out.println("No se pudo escribir en el archivo " + e.getMessage());
        }
        // Leemos del archivo de origen
        try {BufferedReader br = new BufferedReader(new FileReader(origen));
            BufferedWriter bw = new BufferedWriter(new FileWriter(precioAumentado));
            String linea;
            while ((linea = br.readLine())!=null) {
                String[] datos = linea.split(";");
                double precio = Double.parseDouble(datos[2]);// Para pasar el String a Double
                double precio10 = precio * 1.1;
                datos[2] = String.valueOf(precio10);//Para pasar de nuevo el Double a String

                bw.write(datos[0] + ";" + datos[1] + ";" + datos[2]);
                bw.newLine();
            }
            bw.close();
            br.close();
        }catch (IOException e){
            System.out.println("Error al leer del archivo " + e.getMessage());
        }
    }
}