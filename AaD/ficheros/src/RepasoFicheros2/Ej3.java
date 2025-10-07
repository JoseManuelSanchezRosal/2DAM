package RepasoFicheros2;

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
        Scanner sc = new Scanner(System.in);
        File nombres = new File("ficheros/src/RepasoFicheros2/ej3.txt");
        ArrayList<String> alumnos = new ArrayList<>();

        String nombre = "";
        System.out.println("Introduzca nombres, escriba fin para acabar...");
        while (!nombre.equals("fin")){
            nombre = sc.nextLine();
            if (!nombre.equalsIgnoreCase("fin")){
                alumnos.add(nombre);
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(nombres));
            for(String palabra:alumnos){
                bw.write(palabra+"\n");

            }
            bw.close();
        }catch (IOException e){
            System.out.println("Error al escribir el archivo " + e.getMessage());
        }
    }
}