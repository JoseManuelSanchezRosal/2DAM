package repasoExamenPsp;

import java.io.IOException;

/*Ejercicio 1: Crea un programa en Java que crea un hilo y abra firefox o un programa que ya tenéis
abierto y se queda esperando hasta que se cierre. Una vez se cierra escribe un mensaje en
pantalla de que el programa ha sido cerrado y acaba la ejecución.
Nota: Si descubrís algún problema investigad la causa y también cual es una posible
solución.*/

public class Ejercicio1 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("notepad.exe");
        try {
            Process proceso = pb.start();
            System.out.println("El proceso esta abierto");
            proceso.waitFor();
            System.out.println("Proceso cerrado...");
            ;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}