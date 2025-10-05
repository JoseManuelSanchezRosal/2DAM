package Trabajo_A3_PSP.BarberoSemaforo;

import java.util.concurrent.Semaphore;

// La clase semáforo se importa con java.util.concurrent.Semaphore; (es un objeto como podría ser ArrayList)
public class SillonBarberoSemaforo {
    private final int NUM_SILLAS = 3;
    private int sillasDisponibles = NUM_SILLAS;
    private Semaphore clientes = new Semaphore(0);  // semaforo para clientes esperando
    private Semaphore barbero = new Semaphore(0);   // para barbero despierto o no
    private Semaphore mutex = new Semaphore(1);     // para exclusión mutua=mutex
    private int contadorClientes = 0;

    //Método para cortar el pelo:
    public void cortarPelo() throws InterruptedException {
        clientes.acquire(); //espera a que haya un cliente
        mutex.acquire(); //accede a la *sección crítica* - semaforo en rojo
        sillasDisponibles++;
        System.out.println("El barbero está cortando el pelo. Sillas libres: " + sillasDisponibles);
        barbero.release(); //accede al cliente en espera
        mutex.release();//sale de esa sección crítica - semaforo en verde
        Thread.sleep(2000); //2000ms es lo que tarda en hacer el servicio
        System.out.println("Cortando el pelo...Corte hecho\n");
    }

    //Método para espera:
    public void sentarseSillon() throws InterruptedException {
        mutex.acquire();//accede a la sección crítica
        contadorClientes++;
        int id = contadorClientes;
        if (sillasDisponibles > 0) {
            sillasDisponibles--;
            System.out.println("El cliente " + id + " pasa. Sillas libres: " + sillasDisponibles);
            clientes.release(); //un cliente más
            mutex.release(); //semaforo en verde: deja sitio libre
            barbero.acquire(); //entonces el barbero atiende
            System.out.println("El cliente " + id + " se está pelando");
            Thread.sleep(1500); //atiende durante ese tiempo
        } else {
            System.out.println("Está el cliente " + id + " pero no hay sillas libres. (Se va).");
            mutex.release();
        }
    }

    public static void main(String[] args) {
        SillonBarberoSemaforo sillon = new SillonBarberoSemaforo();
        Barberoo barbero = new Barberoo(sillon);
        barbero.start();
        for (int i = 0; i < 10; i++) {
            Clientee cliente = new Clientee(sillon);
            cliente.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}