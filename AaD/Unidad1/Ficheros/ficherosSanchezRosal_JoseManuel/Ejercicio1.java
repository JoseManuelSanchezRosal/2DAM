package Tarea1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//CREAR UN FICHERO CON LA SIGUIENTE ESTRUCTURA: ID:Nombre:Edad(1:Juan:20)
//LEER TODA LA LINEA Y MOSTRAR UNICAMENTE EL NOMBRE EN CADA FILA
public class Ejercicio1 {
    public static void main(String[] args) {
        try {
            File ruta = new File("ficheros/src/Tarea1/fichero1.txt");
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            //Leemos linea por linea
            while ((linea = br.readLine()) !=null){
                //Separamos las lineas en palabras separando por ":"
                String[] palabras = linea.split(":");
                System.out.println(palabras[1]);

//                for (String palabra : palabras){
//                   try {
//                       Integer.parseInt(palabra);
//                   } catch (NumberFormatException e) {
//                       System.out.println(palabra);
//                   }
//                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());//Ponemos esto o e.printstacktrace??
        }
    }
}