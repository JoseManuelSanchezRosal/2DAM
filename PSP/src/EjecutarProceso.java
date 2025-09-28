import java.io.*;
public class EjecutarProceso {
    public static void main(String[] args) {
        //Procesos
        ejemplo1();
        ejemplo2();

        //Hilos
        //ejemplo3();
        //ejemplo4();
    }

    public static void ejemplo1(){
        try {
            ProcessBuilder pb = new ProcessBuilder("x-terminal-emulator");
            Process proceso = pb.start();
            System.out.println("Se ha lanzado una terminal.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Lanza terminal y espera su cierre.
    public static void ejemplo2(){
        try {
            // Lanzar una aplicación gráfica (ej: gedit)
            ProcessBuilder pb = new ProcessBuilder("firefox");
            Process proceso = pb.start();

            System.out.println("Se ha lanzado firefox. Cierra la ventana para continuar...");

            // Esperar a que se cierre la aplicación
            int exitCode = proceso.waitFor();
            System.out.println("firefox se cerró. Código de salida: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ejemplo3(){
        try {
            // ======================
            // EJECUCIÓN SECUENCIAL
            // ======================
            System.out.println("=== Ejecución secuencial ===");
            long inicioSec = System.currentTimeMillis();

            new Contador("Contador 1").secuencial_run(); // .run() = mismo hilo
            new Contador("Contador 2").secuencial_run();
            Contador h3 = new Contador("Contador3");
            h3.secuencial_run();

            long finSec = System.currentTimeMillis();
            System.out.println("Tiempo total secuencial: " + (finSec - inicioSec) / 1000.0 + " segundos\n");


            // ======================
            // EJECUCIÓN CONCURRENTE
            // ======================
            System.out.println("=== Ejecución concurrente (con hilos) ===");
            long inicioCon = System.currentTimeMillis();

            Contador h1 = new Contador("Contador 1");
            Contador h2 = new Contador("Contador 2");

            h1.start();
            h2.start();

            // Esperar a que terminen ambos hilos
            h1.join();
            h2.join();

            long finCon = System.currentTimeMillis();
            System.out.println("Tiempo total concurrente: " + (finCon - inicioCon) / 1000.0 + " segundos");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}