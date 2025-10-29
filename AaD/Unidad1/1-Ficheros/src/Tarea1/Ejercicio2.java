package Tarea1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

//Crear un fichero con un numero en cada fila. Guardar los numeros en un ArrayList<Integer> y mostrar la sumna y la media
public class Ejercicio2 {
    public static void main(String[] args) {
        File ruta = new File("ficheros/src/Tarea1/ficheroNumeros2.txt");
        ArrayList<Integer> numeros = new ArrayList<>();

        try {
            //Leemos los numeros
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine())!=null){
                try {
                    //Los guardamos en el arraylist
                    int numero = Integer.parseInt(linea.trim());//Convertimos a entero
                    numeros.add(numero);
                } catch (NumberFormatException e){
                    System.out.println("Linea no valida o no numerica " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int suma = 0;
        if(!numeros.isEmpty()){
            for (int num : numeros){
                suma += num;
            }
            double media = (double) suma / numeros.size();

            System.out.println("Suma: " + suma);
            System.out.println("Media: " + media);
        }
    }
}