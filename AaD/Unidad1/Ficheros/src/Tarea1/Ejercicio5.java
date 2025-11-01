package Tarea1;
//- -
//Crear fichero productos.txt
//1;Teclado;25.5
//2;Raton;15.0
//3;Monitor;200.0
//Leer todos los productos en memoria, aumentar todos los precios un 10% y
//guardarlos en productos_actualizados.txt

import java.io.*;
import java.util.ArrayList;

public class Ejercicio5 {
    public static void main(String[] args) {
        // Declaramos ruta de archivo original y el nuevo de destino con precios aumentados:
        File archivo = new File("ficheros/src/Tarea1/articulos.txt");
        File archivoNuevo = new File("ficheros/src/Tarea1/articulosMas10.txt");

        try {
            // Abrimos buffer de lectura y escritura:
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoNuevo));

            String linea;
            // Leemos del archivo:
            while ((linea = br.readLine())!=null){
                // Creamos un array de palabras o registros:
                String[] registro = linea.split(";");
                // Cogemos el precio que esta en la 3 posicion de nuestro array:
                double precio = Double.parseDouble(registro[2]);
                // Le aumentamos el precio
                double precioAumentado = precio * 1.1;
                // Y lo almacenamos con el aumento del 10% parseando de nuevo a cadena:
                registro[2] = String.valueOf(precioAumentado);
                // Una vez aumentado, cargamos las lineas en el nuevo archivo con precios aumentados:
                bw.write(registro[0] + ";" + registro[1] + ";" + registro[2]+ "\n");
            }
            // Cerramos los bufferes en orden inverso a su apertura:
            bw.close();
            br.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}