package Trabajo_A3_PSP.BarberoMonitores;

public class Main {
    public static void main(String[] args) {
        // Instanciamos la clase sillón para pasarla por parámetro como RECURSO COMPARTIDO a los 2 procesos
        Sillon sillon = new Sillon();
        Barbero b1 = new Barbero(sillon);
        Cliente c1 = new Cliente(sillon);


        // Lanzamos los hilos (o procesos) barbero y cliente a la vez.

        c1.start();
        b1.start();

        try {
            c1.join();
            b1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
