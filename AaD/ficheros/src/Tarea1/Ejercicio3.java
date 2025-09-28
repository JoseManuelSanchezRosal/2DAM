package Tarea1;
import java.io.*;
import java.util.Scanner;

//Pedir por consola nombres de alumnos hasta que el usuario escriba fin.
//Guardarlos en salida.txt, un nombre por linea
public class Ejercicio3 {
    public static void main(String[] args) {
        String ruta = new String("ficheros/src/Tarea1/ejercicio3.txt");
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduczca nombre: ");
        String nombre = sc.nextLine();


        while (!nombre.equals("fin")){
            try {
                PrintWriter pr = new PrintWriter(new FileWriter(ruta, true));
                pr.println(nombre);
                pr.close();//SE PODIA O NO HACER ESTO???

                System.out.println("Introduzca nombre: ");
                nombre = sc.nextLine();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}