package Trabajo_A3_PSP.BarberoMonitores;

public class Main {
    public static void main(String[] args) {
        // Instanciamos la clase sillón para pasarla por parámetro como RECURSO COMPARTIDO a los 2 procesos
        Sillon sillon = new Sillon();
        Barbero b1 = new Barbero(sillon);
        Cliente c1 = new Cliente(sillon);
        // Lanzamos los hilos....
        c1.start();
        b1.start();
    }
}
