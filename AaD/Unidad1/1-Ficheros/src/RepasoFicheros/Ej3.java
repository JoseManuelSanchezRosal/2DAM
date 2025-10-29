package RepasoFicheros;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*Pedir por consola nombres de alumnos hasta que el usuario escriba "fin"
Guardarlos en salida.txt, un nombre por línea.*/
public class Ej3 {
    public static void main(String[] args) {
        File listaNombres = new File("ficheros/src/RepasoFicheros/Ejercicio3.txt");
        Scanner sc = new Scanner(System.in);

        System.out.println("Escriba algunos nombres \nEscriba fin para terminar");
        String nombre = "";
        while (!nombre.equals("fin")){
            nombre = sc.nextLine();

           try (BufferedWriter bw = new BufferedWriter(new FileWriter(listaNombres, true))){
               if(nombre.equals("fin")){
                   System.out.println("Fin del Programa");
               }else {
                   bw.append(nombre);
                   bw.newLine();
               }
           }catch (IOException e){
               System.out.println("Error al escribir en el archivo: " + e.getMessage());
           }
        }
    }
/*    Notas:
        - Usar .trim en las asignaciones de string para evitar espacios en blanco
        - Usar el equalsIgnoreCase() para ignorar mayusculas o minusculas.*/
}